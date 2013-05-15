package com.seitenbau.earead;

import java.io.File;

import org.apache.log4j.Logger;
import org.sparx.Repository;

import com.seitenbau.common.FileHelper;

/**
 * Provides methods for reading and loading eap files.
 * 
 * @author nkunstek
 */
public class EaReader {

	/** The logger. */
	private static final Logger LOG = Logger.getLogger(EaReader.class);

	/**
	 * Opens and loads a repository for the given eap file.
	 * 
	 * @param eapFile
	 *            the file, not null.
	 * @return the loaded eap file as a repository object.
	 * @throws Exception
	 *             Throws exception when eap cannot be loaded.
	 */
	public static Repository openRepository(File eapFile) throws Exception {
		String method = "openRepository(eapFile): ";
		LOG.debug(method + "Start");

		Repository repository = new Repository();
		// only a file with the absolute path can be loaded
		String absolutePath = eapFile.getAbsolutePath();
		boolean isFileLoaded = repository.OpenFile(absolutePath);
		if (!isFileLoaded) {
			String msg = "Error loading eap file: " + absolutePath;
			LOG.error(method + msg);
			throw new Exception(msg);
		}

		LOG.debug(method + "End");
		return repository;
	}

	/**
	 * Opens and loads a repository for the given relative eap file path name.
	 * 
	 * @param eapFilePath
	 *            the relative file path name of the eap file.
	 * @return the loaded eap file as a repository object.
	 * @throws Exception
	 *             Throws exception when eap cannot be loaded.
	 */
	public static Repository openRepository(String eapFilePath)
			throws Exception {
		String method = "openRepository(eapFilePath): ";
		LOG.debug(method + "Start");

		File eapFile = FileHelper.getFile(eapFilePath);
		Repository repository = openRepository(eapFile);

		LOG.debug(method + "End");
		return repository;
	}
}
