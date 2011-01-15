/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttime.parse;

import com.ttime.logic.Faculty;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

/**
 *
 * @author amit
 */
public class UDonkey implements Parser {

    public UDonkey(File file) throws IOException, ParseException {
    }

    @Override
    public Set<Faculty> getFaculties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
