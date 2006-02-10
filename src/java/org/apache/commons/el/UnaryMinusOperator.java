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

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>The implementation of the unary minus operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public class UnaryMinusOperator
  extends UnaryOperator
{
    
    //-------------------------------------
    // Constants
    //-------------------------------------
    private static Log log = LogFactory.getLog(UnaryMinusOperator.class);
    
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final UnaryMinusOperator SINGLETON =
    new UnaryMinusOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public UnaryMinusOperator ()
  {
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Returns the symbol representing the operator
   **/
  public String getOperatorSymbol ()
  {
    return "-";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public Object apply (Object pValue)
    throws ELException
  {
    if (pValue == null) {
    
      return PrimitiveObjects.getInteger (0);
    }

    else if (pValue instanceof BigInteger) {
        return ((BigInteger) pValue).negate();
    }

    else if (pValue instanceof BigDecimal) {
        return ((BigDecimal) pValue).negate();
    }

    else if (pValue instanceof String) {
      if (Coercions.isFloatingPointString (pValue)) {
	double dval =
	  ((Number) 
	   (Coercions.coerceToPrimitiveNumber 
	    (pValue, Double.class))).
	  doubleValue ();
	return PrimitiveObjects.getDouble (-dval);
      }
      else {
	long lval =
	  ((Number) 
	   (Coercions.coerceToPrimitiveNumber 
	    (pValue, Long.class))).
	  longValue ();
	return PrimitiveObjects.getLong (-lval);
      }
    }

    else if (pValue instanceof Byte) {
      return PrimitiveObjects.getByte 
	((byte) -(((Byte) pValue).byteValue ()));
    }
    else if (pValue instanceof Short) {
      return PrimitiveObjects.getShort 
	((short) -(((Short) pValue).shortValue ()));
    }
    else if (pValue instanceof Integer) {
      return PrimitiveObjects.getInteger 
	((int) -(((Integer) pValue).intValue ()));
    }
    else if (pValue instanceof Long) {
      return PrimitiveObjects.getLong 
	((long) -(((Long) pValue).longValue ()));
    }
    else if (pValue instanceof Float) {
      return PrimitiveObjects.getFloat 
	((float) -(((Float) pValue).floatValue ()));
    }
    else if (pValue instanceof Double) {
      return PrimitiveObjects.getDouble 
	((double) -(((Double) pValue).doubleValue ()));
    }

    else {
        if (log.isErrorEnabled()) {
            String message = MessageUtil.getMessageWithArgs(
                Constants.UNARY_OP_BAD_TYPE,
                getOperatorSymbol(),
                pValue.getClass().getName());
            log.error(message);
            throw new ELException(message);
        }     
      return PrimitiveObjects.getInteger (0);
    }
  }

  //-------------------------------------
}
