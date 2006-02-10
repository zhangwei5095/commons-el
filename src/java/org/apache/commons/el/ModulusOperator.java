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

import java.math.BigInteger;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>The implementation of the modulus operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public class ModulusOperator
  extends BinaryOperator
{
    
    //-------------------------------------
    // Constants
    //-------------------------------------
    private static Log log = LogFactory.getLog(ModulusOperator.class);
    
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final ModulusOperator SINGLETON =
    new ModulusOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ModulusOperator ()
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
    return "%";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public Object apply (Object pLeft, Object pRight)
    throws ELException
  {
    if (pLeft == null &&
	pRight == null) {
        if (log.isWarnEnabled()) {
            log.warn(
                MessageUtil.getMessageWithArgs(
                    Constants.ARITH_OP_NULL, getOperatorSymbol()));
        }     
      return PrimitiveObjects.getInteger (0);
    }

    if ((pLeft != null &&
	 (Coercions.isFloatingPointType (pLeft) ||
	  Coercions.isFloatingPointString (pLeft))) ||
      Coercions.isBigDecimal(pLeft) ||
	(pRight != null &&
	 (Coercions.isFloatingPointType (pRight) ||
	  Coercions.isFloatingPointString (pRight) ||
      Coercions.isBigDecimal(pRight)))) {
      double left =
	Coercions.coerceToPrimitiveNumber (pLeft, Double.class).
	doubleValue ();
      double right =
	Coercions.coerceToPrimitiveNumber (pRight, Double.class).
	doubleValue ();

      try {
	return PrimitiveObjects.getDouble (left % right);
      }
      catch (Exception exc) {
          if (log.isErrorEnabled()) {
              String message = MessageUtil.getMessageWithArgs(
                  Constants.ARITH_ERROR,
                  getOperatorSymbol(),
                  "" + left,
                  "" + right);
              log.error(message);
              throw new ELException(message);
          }	
	return PrimitiveObjects.getInteger (0);
      }
    }
    else if (Coercions.isBigInteger(pLeft) || Coercions.isBigInteger(pRight)) {
        BigInteger left = (BigInteger)
             Coercions.coerceToPrimitiveNumber(pLeft, BigInteger.class);
        BigInteger right = (BigInteger)
            Coercions.coerceToPrimitiveNumber(pRight, BigInteger.class);

        try {
            return left.remainder(right);
        } catch (Exception exc) {
            if (log.isErrorEnabled()) {
                String message = MessageUtil.getMessageWithArgs(
                    Constants.ARITH_ERROR,
                    getOperatorSymbol(),
                    "" + left,
                    "" + right);
                log.error(message);
                throw new ELException(message);
            }	           
            return PrimitiveObjects.getInteger(0);
        }
    }
    else {
      long left =
	Coercions.coerceToPrimitiveNumber (pLeft, Long.class).
	longValue ();
      long right =
	Coercions.coerceToPrimitiveNumber (pRight, Long.class).
	longValue ();

      try {
	return PrimitiveObjects.getLong (left % right);
      }
      catch (Exception exc) {
          if (log.isErrorEnabled()) {
              String message = MessageUtil.getMessageWithArgs(
                  Constants.ARITH_ERROR,
                  getOperatorSymbol(),
                  "" + left,
                  "" + right);
              log.error(message);
              throw new ELException(message);
          }		
	return PrimitiveObjects.getInteger (0);
      }
    }
  }

  //-------------------------------------
}
