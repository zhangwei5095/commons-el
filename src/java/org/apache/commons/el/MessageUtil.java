/*
 * Copyright 1999-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.el;

import java.text.MessageFormat;

/**
 * <p>Utility class for generating parameterized messages.</p> 
 * 
 * @version $Id$
 */

public class MessageUtil
{
 
    /**
     * <p>Returns a formatted message based on the provided template and
     * a single parameter.</p>
     * @param pTemplate the base message
     * @param pArg0 parameter
     * @return Returns a formatted message based on the provided template and
     * a single parameter.
     */ 
    public static String getMessageWithArgs(String pTemplate, Object pArg0) {
        return MessageFormat.format(pTemplate, new Object[]{ "" + pArg0 });
    }
    
    /**
     * <p>Returns a formatted message based on the provided template and
     * provided parameter.</p>
     * @param pTemplate the base message
     * @param pArg0 parameter 1
     * @param pArg1 parameter 2
     * @return Returns a formatted message based on the provided template and
     * provided parameter
     */ 
    public static String getMessageWithArgs(String pTemplate, Object pArg0, Object pArg1) {
        return MessageFormat.format(pTemplate, new Object[]{"" + pArg0, "" + pArg1 });
    }
    
    /**
     * <p>Returns a formatted message based on the provided template and
     * provided parameter.</p>
     * @param pTemplate the base message
     * @param pArg0 parameter 1
     * @param pArg1 parameter 2
     * @param pArg2 parameter 3
     * @return Returns a formatted message based on the provided template and
     * provided parameter
     */ 
    public static String getMessageWithArgs(String pTemplate, Object pArg0, Object pArg1, Object pArg2) {
        return MessageFormat.format(pTemplate, new Object[]{
            "" + pArg0,
            "" + pArg1,
            "" + pArg2
        });
    }
    
    /**
     * <p>Returns a formatted message based on the provided template and
     * provided parameter.</p>
     * @param pTemplate the base message
     * @param pArg0 parameter 1
     * @param pArg1 parameter 2
     * @param pArg2 parameter 3
     * @param pArg3 parameter 4
     * @return Returns a formatted message based on the provided template and
     * provided parameter
     */
    public static String getMessageWithArgs(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3) {
        return MessageFormat.format(
            pTemplate, new Object[]{
                "" + pArg0,
                "" + pArg1,
                "" + pArg2,
                "" + pArg3
            });
    }
    
    /**
     * <p>Returns a formatted message based on the provided template and
     * provided parameter.</p>
     * @param pTemplate the base message
     * @param pArg0 parameter 1
     * @param pArg1 parameter 2
     * @param pArg2 parameter 3
     * @param pArg3 parameter 4
     * @param pArg4 parameter 5
     * @return Returns a formatted message based on the provided template and
     * provided parameter
     */
    public static String getMessageWithArgs(String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3, Object pArg4) {
        return MessageFormat.format(
            pTemplate, new Object[]{
                "" + pArg0,
                "" + pArg1,
                "" + pArg2,
                "" + pArg3,
                "" + pArg4
            });
    }
    
    /**
     * <p>Returns a formatted message based on the provided template and
     * provided parameter.</p>
     * @param pTemplate the base message
     * @param pArg0 parameter 1
     * @param pArg1 parameter 2
     * @param pArg2 parameter 3
     * @param pArg3 parameter 4
     * @param pArg4 parameter 5
     * @param pArg5 parameter 6
     * @return Returns a formatted message based on the provided template and
     * provided parameter
     */
    public static String getMessageWithArgs(
        String pTemplate, Object pArg0, Object pArg1, Object pArg2, Object pArg3,
        Object pArg4, Object pArg5) {
        return MessageFormat.format(
            pTemplate, new Object[]{
                "" + pArg0,
                "" + pArg1,
                "" + pArg2,
                "" + pArg3,
                "" + pArg4,
                "" + pArg5
            });
    }  
}
