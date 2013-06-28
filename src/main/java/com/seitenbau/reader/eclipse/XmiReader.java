package com.seitenbau.reader.eclipse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.XMI2UMLResource;

/**
 * This class offers methods for reading UML models in xmi files using the
 * eclipse libraries ecore und uml from EMF project.
 * 
 * @author nkunstek
 */
public abstract class XmiReader {

	/** The logger. */
	private static final Logger LOG = Logger.getLogger(XmiReader.class);

	/** The XMI version. */
	private static final String XMI_VERSION = "2.1";

	/** The UML version. */
	private static final String UML_VERSION = "2.1";

	/**
	 * Reads the UML model from the given xmi file (Version 2.1) using the
	 * eclipse libraries ecore und uml from EMF project.
	 * 
	 * @param xmiFile
	 *            Xmi file, not null.
	 * @return the UML model.
	 * @throws FileNotFoundException
	 *             File was not found.
	 */
	public static Model readUmlModel(File xmiFile) throws FileNotFoundException {
		String method = "readUmlModel(): ";
		LOG.debug(method + "Start");

		LOG.info("Read UML model from xmi file: " + xmiFile);
		URI uri = URI.createURI(xmiFile.getPath());
		ResourceSet resource = initResourceSet();
		Model model = UML2Util.load(resource, uri, UMLPackage.Literals.MODEL);

		for (Resource uMLResourceImpl : resource.getResources()) {
			if (!uMLResourceImpl.getErrors().isEmpty()) {
				StringBuffer errorMsg = new StringBuffer(
						"ERROR reading Model:\n");
				for (Diagnostic error : uMLResourceImpl.getErrors()) {
					errorMsg.append("Line " + error.getLine() + ": "
							+ error.getMessage() + "\n");
				}
				System.out.println(method + errorMsg);
				LOG.error(method + errorMsg);
			}
			if (!uMLResourceImpl.getWarnings().isEmpty()) {
				StringBuffer warningMsg = new StringBuffer(
						"WARN reading Model:\n");
				for (Diagnostic warning : uMLResourceImpl.getWarnings()) {
					warningMsg.append("Line " + warning.getLine() + ": "
							+ warning.getMessage() + "\n");
				}
				System.out.println(method + warningMsg);
				LOG.warn(method + warningMsg);
			}
		}
		LOG.debug(method + "End");
		return model;
	}

	/**
	 * Initializing the resources.
	 * 
	 * @return The initialized resources.
	 */
	private static ResourceSet initResourceSet() {
		String method = "initResourceSet(): ";
		LOG.debug(method + "Start");
		LOG.debug(method + "Using XMI Version: " + XMI_VERSION);
		LOG.debug(method + "Using UML Version: " + UML_VERSION);

		ResourceSet resourceSet = new ResourceSetImpl();
		Registry packageRegistry = resourceSet.getPackageRegistry();
		packageRegistry.put("http://schema.omg.org/spec/XMI/" + XMI_VERSION,
				UMLPackage.eINSTANCE);
		packageRegistry.put("http://schema.omg.org/spec/UML/" + UML_VERSION,
				UMLPackage.eINSTANCE);

		resourceSet.getLoadOptions().put(
				XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

		Map<String, Object> extensionToFactoryMap = resourceSet
				.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(XMI2UMLResource.FILE_EXTENSION,
				new UMLResourceFactoryImpl());
		// read also xml files additionally to xmi files
		// extensionToFactoryMap.put("xml", new XMIResourceFactoryImpl());

		LOG.debug(method + "End");
		return resourceSet;
	}
}
