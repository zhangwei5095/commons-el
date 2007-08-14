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

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.VariableResolver;

/**
 *
 * <p>This is the JSTL-specific implementation of VariableResolver.
 * It looks up variable references in the PageContext, and also
 * recognizes references to implicit objects.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public class VariableResolverImpl implements VariableResolver {
    // -------------------------------------
    // Member variables
    // -------------------------------------

    private PageContext mCtx;

    // -------------------------------------
    /**
     *
     * Constructor
     **/
    public VariableResolverImpl(PageContext pCtx) {
        mCtx = pCtx;
    }

    // -------------------------------------
    /**
     *
     * Resolves the specified variable within the given context.
     * Returns null if the variable is not found.
     **/
    public Object resolveVariable(String pName) throws ELException {
        // Check for implicit objects
        if ("pageContext".equals(pName)) {
            return mCtx;
        } else if ("pageScope".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getPageScopeMap();
        } else if ("requestScope".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx)
                    .getRequestScopeMap();
        } else if ("sessionScope".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx)
                    .getSessionScopeMap();
        } else if ("applicationScope".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx)
                    .getApplicationScopeMap();
        } else if ("param".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getParamMap();
        } else if ("paramValues".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getParamsMap();
        } else if ("header".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getHeaderMap();
        } else if ("headerValues".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getHeadersMap();
        } else if ("initParam".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getInitParamMap();
        } else if ("cookie".equals(pName)) {
            return ImplicitObjects.getImplicitObjects(mCtx).getCookieMap();
        }

        // Otherwise, just look it up in the page context
        else {
            return mCtx.findAttribute(pName);
        }
    }

    // -------------------------------------
}
