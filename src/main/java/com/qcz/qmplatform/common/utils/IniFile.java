/**
 * Written by Mike Wallace (mfwallace at gmail.com).  Available
 * on the web site http://mfwallace.googlepages.com/.
 * <p>
 * Copyright (c) 2006 Mike Wallace.
 * <p>
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package com.qcz.qmplatform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class provides methods for reading and writing a Windows-style
 * INI file.
 * <p>
 * Colm: modified getBooleanProperty() to use equalsIgnoreCase() when
 * comparing for "true".
 * <p>
 * Sven: added the getAsProperties() method.
 *
 * @author Mike Wallace
 * @author Colm Smyth
 * @author Sven Schliesing
 * @version 1.2
 */
public final class IniFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(IniFile.class);

    /**
     * The name of the INI file.
     */
    private String iniFileName = null;

    /**
     * The line separator for this OS.
     */
    private static final String lineSep;

    /**
     * Get the line separator for the OS.
     */
    static {
        lineSep = System.getProperty("line.separator");
    }


    /**
     * Default constructor.
     */
    private IniFile() {
        super();
    }


    /**
     * Constructor to take the name of the INI file.
     *
     * @param fileName the name of the INI file to read
     */
    public IniFile(final String fileName) {
        if (!new File(fileName).exists()) {
            LOGGER.warn("the file does not exist: {}", fileName);
        }
        iniFileName = fileName;
    }


    /**
     * Returns the property value for the specified section and
     * property.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @return the value for the specified section/property
     */
    public boolean getBooleanProperty(final String sectionName,
                                      final String propertyName) {
        return getBooleanProperty(sectionName, propertyName, false);
    }


    /**
     * Returns the property value for the specified section and
     * property.  If the property is not found, defaultValue
     * is returned instead.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param defaultValue the default value to return, if it's not found
     * @return the value for the specified section/property
     */
    public boolean getBooleanProperty(final String sectionName,
                                      final String propertyName,
                                      final boolean defaultValue) {
        // Get the string from the input file
        final String str = getStringProperty(sectionName, propertyName);

        // Check if the returned string is null
        if (str == null) {
            // It is, so return the default value
            return defaultValue;
        }

        // Declare our boolean variable that gets returned.  The default
        // value is false.
        boolean val = false;

        // Convert the string into a boolean
        if ((str.equals("1")) || (str.equalsIgnoreCase("true"))) {
            // Set val to true for certain string values
            val = true;
        }

        // Return the boolean value
        return val;
    }


    /**
     * Returns the property value for the specified section and
     * property.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @return the value for the specified section/property
     */
    public int getIntegerProperty(final String sectionName,
                                  final String propertyName) {
        return getIntegerProperty(sectionName, propertyName, 0);
    }


    /**
     * Returns the property value for the specified section and
     * property.  If the property is not found, defaultValue
     * is returned instead.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param defaultValue the default value to return, if it's not found
     * @return the value for the specified section/property
     */
    public int getIntegerProperty(final String sectionName,
                                  final String propertyName,
                                  final int defaultValue) {
        // Get the string from the input file
        final String str = getStringProperty(sectionName, propertyName);

        // Check if the returned string is null
        if (str == null) {
            // It is, so return the default value
            return defaultValue;
        }

        // Declare our variable that gets returned.  Set it to
        // the default value in case an exception is thrown while
        // parsing the string.
        int val = defaultValue;

        // Parse the string as a number
        try {
            // Parse the string
            int tempValue = Integer.parseInt(str);

            // If we reach this point, it was a success
            val = tempValue;
        } catch (NumberFormatException nfe) {
            val = defaultValue;
        }

        // Return the value
        return val;
    }


    /**
     * Returns the property value for the specified section and
     * property.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @return the value for the specified section/property
     */
    public long getLongProperty(final String sectionName,
                                final String propertyName) {
        return getLongProperty(sectionName, propertyName, 0L);
    }


    /**
     * Returns the property value for the specified section and
     * property.  If the property is not found, defaultValue
     * is returned instead.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param defaultValue the default value to return, if it's not found
     * @return the value for the specified section/property
     */
    public long getLongProperty(final String sectionName,
                                final String propertyName,
                                final long defaultValue) {
        // Get the string from the input file
        final String str = getStringProperty(sectionName, propertyName);

        // Check if the returned string is null
        if (str == null) {
            // It is, so return the default value
            return defaultValue;
        }

        // Declare our variable that gets returned.  Set it to
        // the default value in case an exception is thrown while
        // parsing the string.
        long val = defaultValue;

        // Parse the string as a number
        try {
            // Parse the string
            long tempValue = Long.parseLong(str);

            // If we reach this point, it was a success
            val = tempValue;
        } catch (NumberFormatException nfe) {
            val = defaultValue;
        }

        // Return the value
        return val;
    }


    /**
     * Returns the property value for the specified section and
     * property.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @return the value for the specified section/property
     */
    public double getDoubleProperty(final String sectionName,
                                    final String propertyName) {
        return getDoubleProperty(sectionName, propertyName, 0.0);
    }


    /**
     * Returns the property value for the specified section and
     * property.  If the property is not found, defaultValue
     * is returned instead.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param defaultValue the default value to return, if it's not found
     * @return the value for the specified section/property
     */
    public double getDoubleProperty(final String sectionName,
                                    final String propertyName,
                                    final double defaultValue) {
        // Get the string from the input file
        final String str = getStringProperty(sectionName, propertyName);

        // Check if the returned string is null
        if (str == null) {
            // It is, so return the default value
            return defaultValue;
        }

        // Declare our variable that gets returned.  Set it to
        // the default value in case an exception is thrown while
        // parsing the string.
        double val = defaultValue;

        // Parse the string as a number
        try {
            // Parse the string
            double tempValue = Double.parseDouble(str);

            // If we reach this point, it was a success
            val = tempValue;
        } catch (NumberFormatException nfe) {
            val = defaultValue;
        }

        // Return the value
        return val;
    }


    /**
     * Returns the property value for the specified section and
     * property.  If the property is not found, defaultValue
     * is returned instead.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @return the value for the specified section/property
     */
    public Date getDateProperty(final String sectionName,
                                final String propertyName) {
        // Get the string from the input file
        final String str = getStringProperty(sectionName, propertyName);

        // Check if the returned string is null
        if (str == null) {
            // It is, so return the default value
            return null;
        }

        // Declare our variable that gets returned.  Set it to
        // the default value in case an exception is thrown while
        // parsing the string.
        Date date = null;

        // Parse the string as a number
        try {
            // Parse the string
            long tempValue = Long.parseLong(str);

            // If we reach this point, the long was parsed correctly
            if (tempValue >= 0) {
                // Create a Date object using the temp value
                date = new Date(tempValue);
            }
        } catch (NumberFormatException nfe) {
            // Default to the current time
            date = new Date(System.currentTimeMillis());
        }

        // Return the value
        return date;
    }


    /**
     * Returns a string property for the specified section and
     * property.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @return the value for the specified section/property
     */
    public String getStringProperty(final String sectionName,
                                    final String propertyName) {
        // Make null the default value if the section/property combo is not found
        return getStringProperty(sectionName, propertyName, null);
    }


    /**
     * Returns a string property for the specified section and
     * property.  If the property is not found, defaultValue
     * is returned instead.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param defaultValue the default value to return, if it's not found
     * @return the value for the specified section/property
     */
    public String getStringProperty(final String sectionName,
                                    final String propertyName,
                                    final String defaultValue) {
        // Check the section name
        if ((sectionName == null) || (sectionName.length() < 1)) {
            throw new RuntimeException(
                    "The name of the section has not been specified");
        }
        // Check the property name
        else if ((propertyName == null) || (propertyName.length() < 1)) {
            throw new RuntimeException(
                    "The name of the property has not been specified");
        }

        // Verify the file exists and is a file
        if (!iniFileExists()) {
            return defaultValue;
        }

        // The current section name
        String currentSection = null;

        // The current property name
        String currentProperty = null;

        // This will hold the value that gets returned
        String value = defaultValue;

        // Declare the reader
        BufferedReader in = null;

        // Open the input file and gather the section names
        try {
            // Open the file reader
            in = new BufferedReader(new FileReader(iniFileName));

            // This will hold each line read from the file
            String str;

            // Whether to continue processing
            boolean bContinue = true;

            // Read each line until we hit the end of the file
            while ((bContinue) && ((str = in.readLine()) != null)) {
                // Check if the line has some length and starts with a [
                if ((str.length() > 2) && (str.charAt(0) == '[')) {
                    // Check if the line has a ]
                    int lastIndex = str.indexOf(']');
                    if (lastIndex > 0) {
                        // It does, so save everything between the [
                        // and ] as a section name
                        currentSection = str.substring(1, lastIndex);
                    }
                } else {
                    // Check if we're in the specified section
                    if ((currentSection != null) && (currentSection.equals(sectionName))) {
                        // Save the property name (ignore comments)
                        final int commentIndex = str.indexOf(';');
                        final int equalsIndex = str.indexOf('=');
                        if ((equalsIndex > 0) && ((commentIndex < 0) ||
                                (commentIndex > equalsIndex))) {
                            currentProperty = str.substring(0, equalsIndex).trim();
                        } else {
                            currentProperty = null;
                        }

                        // See if we're in the right property
                        if ((currentProperty != null) &&
                                (currentProperty.equals(propertyName))) {
                            // We are, so check the line
                            if (equalsIndex < (str.length() - 1)) {
                                // There is a string after the equals sign, so save it
                                value = str.substring(equalsIndex + 1).trim();

                                // Stop processing
                                bContinue = false;
                            } else {
                                // The line ends with an '=', so set the value to an
                                // empty string and stop reading the input file
                                value = "";
                                bContinue = false;
                            }
                        }
                    }
                }
            }

            // Close the input stream
            in.close();
            in = null;
        } catch (IOException ioe) {
            // Verify the file reader is closed
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    in = null;
                }

                in = null;
            }

            // Throw the exception
            throw new RuntimeException(ioe.getMessage());
        }

        // Return the value for this property
        return value;
    }


    /**
     * Writes the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    public boolean setBooleanProperty(final String sectionName,
                                      final String propertyName,
                                      final boolean value) {
        // Save the value as a string
        final String sValue = Boolean.toString(value);

        // Pass the string to setStringProperty
        return setStringProperty(sectionName, propertyName, sValue);
    }


    /**
     * Writes the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    public boolean setIntegerProperty(final String sectionName,
                                      final String propertyName,
                                      final int value) {
        // Save the value as a string
        final String sValue = Integer.toString(value);

        // Pass the string to setStringProperty
        return setStringProperty(sectionName, propertyName, sValue);
    }


    /**
     * Writes the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    public boolean setLongProperty(final String sectionName,
                                   final String propertyName,
                                   final long value) {
        // Save the value as a string
        final String sValue = Long.toString(value);

        // Pass the string to setStringProperty
        return setStringProperty(sectionName, propertyName, sValue);
    }


    /**
     * Writes the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    public boolean setDoubleProperty(final String sectionName,
                                     final String propertyName,
                                     final double value) {
        // Save the value as a string
        final String sValue = Double.toString(value);

        // Pass the string to setStringProperty
        return setStringProperty(sectionName, propertyName, sValue);
    }


    /**
     * Writes the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    public boolean setDateProperty(final String sectionName,
                                   final String propertyName,
                                   final Date value) {
        // Check the date
        if (value == null) {
            // It's null, so pass null to setStringProperty
            return setStringProperty(sectionName, propertyName, null);
        }

        // Convert the date to milliseconds
        final long lDateInMS = value.getTime();

        // Convert the milliseconds to a string
        final String sDate = Long.toString(lDateInMS);

        // Pass the milliseconds string to setStringProperty
        return setStringProperty(sectionName, propertyName, sDate);
    }


    /**
     * Writes the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    public boolean setStringProperty(final String sectionName,
                                     final String propertyName,
                                     final String value) {
        // Declare the result code
        boolean rc = true;

        // Check the inputs
        if ((sectionName == null) || (propertyName == null)) {
            // The section and property cannot be null
            return false;
        } else if ((sectionName.length() < 1) || (propertyName.length() < 1)) {
            // The section and property cannot be empty
            return false;
        }

        // Save whether the input file exists
        final boolean fileExists = iniFileExists();

        // See if the section is already in the file
        final boolean sectionExists = containsSection(sectionName);

        // Handle the case of the section not existing
        if (!sectionExists) {
            // Add the section, property and value to the file
            rc = addToFile(sectionName, propertyName, value, fileExists);
        } else {
            // Handle the case of the section existing. Get the property's
            // current value
            final String oldValue = getStringProperty(sectionName, propertyName);

            // If oldValue is not null, then the property exists; handle
            // that case first
            if (oldValue != null) {
                // The property exists.  See if it has the value we want to save.
                if ((value != null) && (oldValue.equals(value))) {
                    // The values match, so there's nothing to do
                    rc = true;
                } else {
                    // The section and property exist, so update property
                    rc = updatePropertyInSection(sectionName, propertyName, value);
                }
            } else {
                // The section exists, but not the property, so we have
                // to add the property to the section
                rc = addPropertyToSection(sectionName, propertyName, value);
            }
        }

        // Return the result of the operation
        return rc;
    }


    /**
     * Modifies the property's value for the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @param fileExists   whether the file exists
     * @return the success of the operation
     */
    private boolean addToFile(final String sectionName,
                              final String propertyName,
                              final String value,
                              final boolean fileExists) {
        // Our return code
        boolean rc = true;

        // Declare our file writer
        BufferedWriter out = null;

        // Append a blank line, and the the section, and property=value
        try {
            // Open the file for appending
            out = new BufferedWriter(new FileWriter(iniFileName, true));

            // If the file exists, write two newlines to the file
            if (fileExists) {
                out.write(lineSep);
                out.write(lineSep);
            }

            // Build the string to write out
            StringBuilder sb = new StringBuilder(100);

            // Write the section name
            sb.append("[").append(sectionName).append("]").append(lineSep);

            // Check if value is null
            if (value != null) {
                // It's not null, so write the property and value
                sb.append(propertyName).append("=").append(value).append(lineSep);
            }

            // Append the string to the end of the file
            out.write(sb.toString());

            // Close the output buffer
            out.close();
            out = null;
        } catch (IOException ioe) {
            rc = false;
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                }

                out = null;
            }

            // Throw the exception
            throw new RuntimeException(ioe.getMessage());
        }

        return rc;
    }


    /**
     * Modifies the property's value for the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    private boolean updatePropertyInSection(final String sectionName,
                                            final String propertyName,
                                            final String value) {
        // We need to replace the line in the file, so
        // read all lines from the file and store here
        List<String> fileLines = getLinesFromFile();

        // Store the lines we will later write to the file
        List<String> outLines = new ArrayList<String>(fileLines.size());

        // This is the name of the current section
        String currSection = null;

        // Iterate over the lines
        for (String line : fileLines) {
            // Record whether this is the line with the right
            // property and we're in the right section
            boolean bFound = false;

            // See if we're in a new section
            final String tempSection = getSectionNameFromLine(line);
            if (tempSection != null) {
                // We're at the start of a section, so save the name
                currSection = tempSection;
            } else {
                // Check if we're in the right section
                if ((currSection != null) && (currSection.equals(sectionName))) {
                    // Get the name of the property on this line
                    final String currProp = getPropertyNameFromLine(line);

                    // Check if the property name matches
                    if ((currProp != null) && (currProp.equals(propertyName))) {
                        // Check if value is null
                        if (value == null) {
                            // It is, so don't write this line
                            bFound = true;
                        } else {
                            // We found the property
                            bFound = true;

                            // Build the line
                            StringBuilder sb = new StringBuilder(100);
                            sb.append(propertyName).append("=").append(value);

                            // Save it to the output array
                            outLines.add(sb.toString());
                        }
                    }
                }
            }

            // Check if the line was found
            if (!bFound) {
                // It wasn't, so we can just write out the line
                outLines.add(line);
            }
        }

        // Write outLines to the file
        writeArrayToFile(outLines);

        // Return success
        return true;
    }


    /**
     * Adds the propertyName=value to the specified section.
     *
     * @param sectionName  the name of the section
     * @param propertyName the name of the property
     * @param value        the property value
     * @return the success of the operation
     */
    private boolean addPropertyToSection(final String sectionName,
                                         final String propertyName,
                                         final String value) {
        // If value is null, just return
        if (value == null) {
            return true;
        }

        // Read all lines from the file and store here
        List<String> fileLines = getLinesFromFile();

        // Store the lines we will later write to the file
        List<String> outLines = new ArrayList<String>(fileLines.size() + 1);

        // This is the name of the current section
        String currSection = null;

        // Save the name of the previous section
        String prevSection = null;

        // Save whether we hit the end of the matching section
        boolean foundSectionEnd = false;

        // Iterate over the lines
        for (String line : fileLines) {
            // Record whether this is the line with the right
            // property and we're in the right section
            boolean bFound = false;

            // See if we're in a new section
            final String tempSection = getSectionNameFromLine(line);
            if (tempSection != null) {
                // We're at the start of a section, so update the
                // prevSection variable and then update currSection
                prevSection = currSection;
                currSection = tempSection;

                if ((prevSection != null) &&
                        (prevSection.equals(sectionName)) &&
                        (!foundSectionEnd)) {
                    // We got to the section after the one we wanted, and
                    // we didn't write out the property line, so there must
                    // not have been a blank line at the end of the section.
                    // Now we need to write out the property line, followed
                    // by a blank line.

                    // Build the line
                    StringBuilder sb = new StringBuilder(100);
                    sb.append(propertyName).append("=").append(value);
                    sb.append(lineSep);

                    // Save it to the output array
                    outLines.add(sb.toString());

                    // Update that we hit the end of the section
                    foundSectionEnd = true;
                }
            } else {
                // Check that we're in a section
                if (currSection != null) {
                    // Check that we're in the right section
                    if (currSection.equals(sectionName)) {
                        // See if we've not written the property yet,
                        // and the line is empty
                        if ((!foundSectionEnd) && (line.length() < 1)) {
                            // The line is blank, so assume we're at the end
                            // of a section; add the property name here
                            // We found the property
                            bFound = true;

                            // Build the line
                            StringBuilder sb = new StringBuilder(100);
                            sb.append(propertyName).append("=").append(value);
                            sb.append(lineSep);

                            // Save it to the output array
                            outLines.add(sb.toString());

                            // Update that we hit the end of the section
                            foundSectionEnd = true;
                        }
                    }
                }
            }

            // Check if the line was found
            if (!bFound) {
                // It wasn't, so we can just write out the line
                outLines.add(line);
            }
        }

        // Check if we never found the section end
        if (!foundSectionEnd) {
            // The section must have been the last section
            // in the file, and didn't end with a blank
            // line, so add the line now.

            // Build the line
            StringBuilder sb = new StringBuilder(100);
            sb.append(propertyName).append("=").append(value);

            // Save it to the output array
            outLines.add(sb.toString());
        }

        // Write outLines to the file
        writeArrayToFile(outLines);

        // Return success
        return true;
    }


    /**
     * Write the array of Strings to the output file.
     *
     * @param outLines the array of Strings to write
     */
    private void writeArrayToFile(final List<String> outLines) {
        // Check the input
        if (outLines == null) {
            return;
        }

        // Declare our file writer
        BufferedWriter out = null;

        // Append a blank line, and the the section, and property=value
        try {
            // Open the file for writing
            out = new BufferedWriter(new FileWriter(iniFileName, false));

            // Iterate over the array
            for (String str : outLines) {
                // Write each line, followed by a line separator
                out.write(str);
                out.write(lineSep);
            }

            // Close the output buffer
            out.close();
            out = null;
        } catch (IOException ioe) {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                }

                out = null;
            }

            // Throw the exception
            throw new RuntimeException(ioe.getMessage());
        }
    }


    /**
     * Parse the property name from the line.
     *
     * @param line the line from the input file
     * @return the property name, or null if this isn't a property line
     */
    private String getPropertyNameFromLine(final String line) {
        // Check the input
        if ((line == null) || (line.length() < 2)) {
            return null;
        }

        // Save the property name (ignore comments)
        final int commentIndex = line.indexOf(';');
        final int equalsIndex = line.indexOf('=');
        if ((equalsIndex > 0) && ((commentIndex < 0) ||
                (commentIndex > equalsIndex))) {
            String propName = line.substring(0, equalsIndex).trim();
            return propName;
        }

        // The line does not have a property
        return null;
    }


    /**
     * Parse the section name from the line.
     *
     * @param line the input line from the file
     * @return the parsed section name, or null if there is no section name
     */
    private String getSectionNameFromLine(final String line) {
        // Check the input
        if ((line == null) || (line.length() < 3)) {
            return null;
        }

        // Check if the line starts with a [
        if (line.charAt(0) == '[') {
            // Check if the line has a ]
            int lastIndex = line.indexOf(']');
            if (lastIndex > 0) {
                // It does, so save everything between the [ and ] as a section name
                return (line.substring(1, lastIndex));
            }
        }

        // No section name found
        return null;
    }


    /**
     * Read the INI file into memory.
     *
     * @return the contents of the INI file
     */
    private List<String> getLinesFromFile() {
        // This will hold the file contents
        List<String> fileLines = new ArrayList<String>(200);

        // Declare our file reader
        BufferedReader in = null;

        // Open the input file and gather the section names
        try {
            // Open the file reader
            in = new BufferedReader(new FileReader(iniFileName));

            // This will hold each line read from the file
            String str;

            // Read each line until we hit the end of the file
            while ((str = in.readLine()) != null) {
                fileLines.add(str);
            }

            // Close the input stream
            in.close();
            in = null;
        } catch (IOException ioe) {
            // Verify the file reader is closed
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    in = null;
                }

                in = null;
            }

            // Throw the exception
            throw new RuntimeException(ioe.getMessage());
        }

        // Return the array of lines
        return fileLines;
    }


    /**
     * Return whether the specified section name is in the
     * input file.
     *
     * @param sectionName the section name of interest
     * @return whether sectionName is a section in the file
     */
    public boolean containsSection(final String sectionName) {
        return findSection(sectionName, null) != null;
    }


    /**
     * Return an array of the names of all properties in the given section.
     *
     * @param sectionName the name of the section of interest
     * @return an array of the property names in the section
     */
    public List<String> getAllPropertyNames(final String sectionName) {
        // Check the input parameter
        if ((sectionName == null) || (sectionName.length() < 1)) {
            throw new RuntimeException(
                    "The name of the section was not specified");
        }

        // Declare our array to hold the string that will get returned
        List<String> props = new ArrayList<String>(20);

        // Declare the file reader
        BufferedReader in = findSection(sectionName, null);

        // Open the input file and gather the section names
        try {
            // This will hold each line read from the file
            String str;

            // Read each line until we hit the end of the file
            while ((str = in.readLine()) != null) {
                // Try to parse this line as a section name
                String tempSectionName = getSectionNameFromLine(str);

                // See if it was successful
                if (tempSectionName == null) {
                    // Try to parse the property name from the line
                    String propName = getPropertyNameFromLine(str);
                    if (propName != null) {
                        props.add(propName);
                    }
                } else {
                    break; // found the start of the next section
                }
            }
            // Close the input stream
            in.close();
        } catch (IOException ioe) {
            // Verify the file reader is closed
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }

            // Throw the exception
            throw new RuntimeException(ioe.getMessage());
        }

        // Return the list of section names
        return props;
    }


    /**
     * Return the section names as a hash set.
     *
     * @return a hash set of section names
     */
    public Set<String> getAllSectionNamesAsHashSet() {
        // This will hold the data we return
        Set<String> sectionsSet = new HashSet<String>();
        findSection(null, sectionsSet);

        // Return the set
        return sectionsSet;
    }


    /**
     * Return an array of section names.
     *
     * @return an array of section names
     */
    public List<String> getAllSectionNames() {
        // Declare our array to hold the string that will get returned
        List<String> sections = new ArrayList<String>(20);

        findSection(null, sections);
        return sections;
    }


    /**
     * Return whether the file exists.  This will throw an
     * exception if the file name variable is null or
     * empty, or if the filename points to a directory.
     *
     * @return whether the file exists
     */
    private boolean iniFileExists() {
        // Check the input filename
        if ((iniFileName == null) || (iniFileName.length() < 1)) {
            throw new RuntimeException(
                    "The name of the INI file has not been specified");
        }

        // Declare our result code variable
        boolean rc = true;

        // Create a file object for the file
        File file = new File(iniFileName);

        // Check if the file exists
        if (!file.exists()) {
            // The file doesn't exist
            rc = false;
        }
        // Check if the file is a directory
        else if (!file.isFile()) {
            // The file is a directory
            file = null;
            throw (new RuntimeException("The specified file is a directory"));
        }

        // Set the file object to null
        file = null;

        // Return the result code
        return rc;
    }


    /**
     * (Colm) Gets all properties in all sections. The map returned
     * is keyed on the section name; the value for each key is a map
     * of the properties for that section.
     *
     * @return map containing properties for all sections
     */
    public Map<String, Map<String, String>> getAllPropertiesInAllSections() {
        List<String> sections = new ArrayList<String>();

        // Open the file reader
        BufferedReader in = findSection("", sections);
        if (in == null) {
            return null;
        }

        Map<String, Map<String, String>> allProps =
                new HashMap<String, Map<String, String>>();
        while (in != null) {
            String currentSectionName = sections.get(sections.size() - 1);
            Map<String, String> props = new HashMap<String, String>(10);
            in = getAllPropertiesInCurrentSection(props, in, sections);
            allProps.put(currentSectionName, props);
        }

        sections.clear(); // help GC, not essential
        return allProps;
    }


    /**
     * (Colm) Return an array of the names of all properties in
     * the given section.
     *
     * @param sectionName the name of the section of interest
     * @return an array of the property names in the section
     */
    public Map<String, String> getAllPropertiesInSection(final String sectionName) {
        return getAllPropertiesInSection(sectionName, null);
    }


    /**
     * (Colm) Return an array of the names of all properties in the
     * given section.
     *
     * @param sectionName the name of the section of interest
     * @param props       the map into which to get the properties;
     *                    if null, a new map is created
     * @return an array of the property names in the section
     */
    public Map<String, String> getAllPropertiesInSection(final String sectionName,
                                                         Map<String, String> props) {
        if (props == null) {
            props = new HashMap<String, String>(20);
        }

        // Open the file reader
        BufferedReader in = findSection(sectionName, null);
        getAllPropertiesInCurrentSection(props, in, null);
        return props;
    }


    /**
     * (Colm) Return an array of the names of all properties in the given section.
     *
     * @param props    the map into which to get the properties; if null,
     *                 a new map is created
     * @param in       the input reader
     * @param sections the list of sections
     * @return the BufferedReader, or null if all properties hav been read
     */
    public BufferedReader getAllPropertiesInCurrentSection(
            final Map<String, String> props,
            final BufferedReader in,
            final Collection<String> sections) {
        if (in == null) {
            return null; // section not found
        }

        try {
            // This will hold each line read from the file
            String str;

            // Read each line until we hit the end of the file
            while ((str = in.readLine()) != null) {
                if (str.length() == 0 || str.charAt(0) == ';') {
                    // skip blank or comment line
                    continue;
                }

                // Try to parse this line as a section name
                String tempSectionName = getSectionNameFromLine(str);

                if (tempSectionName != null) {
                    // found a section name, end of properties in the desired section
                    if (sections != null) {
                        sections.add(tempSectionName);
                    }

                    return in;
                }

                // This line is not a section name
                String propName = getPropertyNameFromLine(str);
                if (propName != null) {
                    int eq = str.indexOf('=');
                    String propValue = str.substring(eq + 1);
                    props.put(propName, propValue);
                }
            }

            return null;
        } catch (Exception e) {
            try {
                in.close();
            } catch (IOException e1) {
            }

            return null;
        }
    }


    /**
     * (Colm) Return the file if the filename is valid and the filename
     * identifies a readable file.
     *
     * @return the file for the ini's filename
     */
    private File getIniFile() {
        // Check the input filename
        if ((iniFileName == null) || (iniFileName.length() < 1)) {
            throw new RuntimeException(
                    "The name of the INI file has not been specified");
        }

        // Create a file object for the file
        File file = new File(iniFileName);

        // Check if the file exists
        if (!file.exists()) {
            return null;
        } else if (!file.isFile()) {
            // It's a directory
            throw (new RuntimeException("The specified file is a directory"));
        } else if (!file.canRead()) {
            // It cannot be read
            throw (new RuntimeException("The specified file cannot be read"));
        }

        return file;
    }


    /**
     * (Colm) This method will search for the specified sectionName and
     * return a BufferedReader to the iniFile if the section is found.
     * Some optional behaviour:
     * - if sections is non-null, the names of any sections found are
     * added to it
     * - if sectionName is null, or if the section is not found, null
     * will be returned
     * - if sectionName is "", then the BufferedReader will be returned
     * when the first section is found
     *
     * @param sectionName the section to find; may be null
     * @param sections    if non-null, all section names found are added to it
     * @return the BufferedReader after finding the section, or null if not found
     */
    private BufferedReader findSection(final String sectionName,
                                       final Collection<String> sections) {
        // Declare our file reader
        BufferedReader in = null;

        // Open the input file and gather the section names
        try {
            File iniFile = getIniFile();

            // Open the file reader
            in = new BufferedReader(new FileReader(iniFile));
            return findSection(sectionName, in, sections);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * (Colm) Given a BufferedReader, search for the specified sectionName.
     * Some optional behaviour:
     * - if sections is non-null, the names of any sections found are added to it
     * - if sectionName is null, or if the section is not found, the BufferedReader
     * will be close()d.
     * - if sectionName is "", then the BufferedReader will be returned when
     * the first section is found
     *
     * @param sectionName the section to find; may be null
     * @param in          the BufferedReader to read from
     * @param sections    if non-null, all section names found are added to it
     * @return the BufferedReader after finding the section, or null if not found
     */
    private BufferedReader findSection(final String sectionName,
                                       final BufferedReader in,
                                       final Collection<String> sections) {
        try {
            // This will hold each line read from the file
            String str;
            // Read each line until we hit the end of the file
            while ((str = in.readLine()) != null) {
                String tempName = getSectionNameFromLine(str);
                if (tempName != null) {
                    if (sections != null) {
                        // if sections is non-null, the names of any sections
                        // found are added to it
                        sections.add(tempName);
                    }
                    if (sectionName != null && sectionName.length() == 0) {
                        // if sectionName is "", then the BufferedReader will be
                        // returned when the first section is found
                        return in;
                    }
                    if (tempName.equals(sectionName)) {
                        return in; // return the Reader so that the caller can continue
                    }
                }
            }

            // failed to find the section; close the Reader
            in.close();
            return null;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }


    /**
     * (Sven) Return the INI contents as a Properties object.
     *
     * @return the INI contents as a Properties object
     */
    public Properties getAsProperties() {
        // Declare the Properties object
        Properties prop = new Properties();

        // Get all properties in all sections
        Map<String, Map<String, String>> iniProperties =
                this.getAllPropertiesInAllSections();

        // Iterate over the sections
        for (Map.Entry<String, Map<String, String>> sectionEntry : iniProperties.entrySet()) {
            // Iterate over the properties in the section
            String propertyKeyPrefix = sectionEntry.getKey();
            for (Map.Entry<String, String> entry : sectionEntry.getValue().entrySet()) {
                // Add the section name and the property name as a key,
                // with the property value as the value
                prop.put(propertyKeyPrefix + "." + entry.getKey(), entry.getValue());
            }
        }

        // Return the properties
        return prop;
    }


    /**
     * Returns the name of the underlying INI file.
     *
     * @return the name of the INI file
     */
    public String getIniFilename() {
        return iniFileName;
    }
}
