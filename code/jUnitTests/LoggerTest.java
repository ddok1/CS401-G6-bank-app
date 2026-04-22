package jUnitTests;

import bankapp.Log;
import bankapp.Logger;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LoggerTest {

	private Logger logger;
	private String originalFilename;

	@TempDir
	Path tempDir;

	@BeforeEach
	void setUp() {
		logger = Logger.getInstance();
		originalFilename = logger.getFilename();

		String tempFile = tempDir.resolve("test-logs.txt").toString();
		logger.setFilename(tempFile);
		logger.clearLogs();
	}

	@AfterEach
	void tearDown() {
		logger.clearLogs();
		logger.setFilename(originalFilename);
	}

	@Test
	void testSingletonInstance() {
		Logger logger2 = Logger.getInstance();
		assertSame(logger, logger2);
	}

	@Test
	void testLogEventAddsLog() {
		Log log = new Log(Log.TRANSACTION_TYPE.DEPOSIT, "deposit test", 100.0);

		logger.logEvent(log);

		ArrayList<Log> logs = logger.getLogs();
		assertEquals(1, logs.size());
		assertEquals(Log.TRANSACTION_TYPE.DEPOSIT, logs.get(0).getType());
		assertEquals("deposit test", logs.get(0).getComment());
		assertEquals(100.0, logs.get(0).getAmount(), 0.0001);
	}

	@Test
	void testGetLogsReturnsCopy() {
		logger.logEvent(new Log(Log.TRANSACTION_TYPE.OTHER, "copy test", 1.0));

		ArrayList<Log> logs = logger.getLogs();
		logs.clear();

		assertEquals(1, logger.getLogs().size());
	}

	@Test
	void testSaveLogsWritesFile() throws IOException {
		logger.logEvent(new Log(Log.TRANSACTION_TYPE.WITHDRAWAL, "save test", 25.0));
		logger.saveLogs();

		File file = new File(logger.getFilename());
		assertTrue(file.exists());
		assertTrue(file.length() > 0);
	}

	@Test
	void testReloadLogsReadsSavedFile() {
		logger.logEvent(new Log(Log.TRANSACTION_TYPE.TRANSFER, "reload test", 75.5));
		logger.saveLogs();

		logger.clearLogs();
		assertEquals(0, logger.getLogs().size());

		logger.reloadLogs();

		ArrayList<Log> logs = logger.getLogs();
		assertEquals(1, logs.size());
		assertEquals(Log.TRANSACTION_TYPE.TRANSFER, logs.get(0).getType());
		assertEquals("reload test", logs.get(0).getComment());
		assertEquals(75.5, logs.get(0).getAmount(), 0.0001);
	}

	@Test
	void testReloadLogsSkipsBadLines() throws IOException {
		FileWriter writer = new FileWriter(logger.getFilename());
		writer.write("1000|DEPOSIT|good line|50.0" + System.lineSeparator());
		writer.write("bad line here" + System.lineSeparator());
		writer.write("2000|ERROR|another good line|10.0" + System.lineSeparator());
		writer.close();

		logger.reloadLogs();

		ArrayList<Log> logs = logger.getLogs();
		assertEquals(2, logs.size());
		assertEquals(Log.TRANSACTION_TYPE.DEPOSIT, logs.get(0).getType());
		assertEquals(Log.TRANSACTION_TYPE.ERROR, logs.get(1).getType());
	}

	@Test
	void testClearLogsRemovesAllLogs() {
		logger.logEvent(new Log(Log.TRANSACTION_TYPE.DEPOSIT, "a", 1.0));
		logger.logEvent(new Log(Log.TRANSACTION_TYPE.WITHDRAWAL, "b", 2.0));

		assertEquals(2, logger.getLogs().size());

		logger.clearLogs();

		assertEquals(0, logger.getLogs().size());
	}
}