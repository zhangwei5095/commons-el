/* $Id$ */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;

/**
 * A subclass of <code>FunctionInvocation</code> which is bound
 * to a particular <code>Method</code>.  A bound function does
 * not require a <code>FunctionMapper</code> to be evaluated.
 * 
 * @author Jamie Taylor
 */
public class BoundFunctionInvocation extends FunctionInvocation {
    private final Method method;
    
    /**
     * @param functionName
     * @param argumentList
     */
    public BoundFunctionInvocation(
            final Method method,
            final String functionName, 
            final List argumentList) 
    {
        super(functionName, argumentList);
        this.method = method;
    }

    /**
     * Returns the <code>Method</code>supplied to the constructor.
     * @param functions unused
     */
    protected Method resolveFunction(FunctionMapper functions)
            throws ELException {
        return method;
    }
}
