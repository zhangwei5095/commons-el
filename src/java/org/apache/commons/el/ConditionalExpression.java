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

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 *
 * <p>Represents a conditional expression.  I've decided not to produce an
 * abstract base "TernaryOperatorExpression" class since (a) future ternary
 * operators are unlikely and (b) it's not clear that there would be a
 * meaningful way to abstract them.  (For instance, would they all be right-
 * associative?  Would they all have two fixed operator symbols?)
 * 
 * @author Shawn Bayern
 **/

public class ConditionalExpression
  extends Expression
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property condition

  Expression mCondition;
  public Expression getCondition ()
  { return mCondition; }
  public void setCondition (Expression pCondition)
  { mCondition = pCondition; }

  //-------------------------------------
  // property trueBranch

  Expression mTrueBranch;
  public Expression getTrueBranch ()
  { return mTrueBranch; }
  public void setTrueBranch (Expression pTrueBranch)
  { mTrueBranch = pTrueBranch; }

  //-------------------------------------
  // property falseBranch

  Expression mFalseBranch;
  public Expression getFalseBranch ()
  { return mFalseBranch; }
  public void setFalseBranch (Expression pFalseBranch)
  { mFalseBranch = pFalseBranch; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ConditionalExpression (Expression pCondition,
				Expression pTrueBranch,
				Expression pFalseBranch)
  {
    mCondition = pCondition;
    mTrueBranch = pTrueBranch;
    mFalseBranch = pFalseBranch;
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    return
      "( " + mCondition.getExpressionString() + " ? " + 
      mTrueBranch.getExpressionString() + " : " +
      mFalseBranch.getExpressionString() + " )";
  }

  //-------------------------------------
  /**
   *
   * Evaluates the conditional expression and returns the literal result
   **/
  public Object evaluate (VariableResolver vr,
			  FunctionMapper f)
    throws ELException
  {
    // first, evaluate the condition (and coerce the result to a boolean value)
    boolean condition =
      Coercions.coerceToBoolean(
        mCondition.evaluate(vr, f)).booleanValue();

    // then, use this boolean to branch appropriately
    if (condition)
      return mTrueBranch.evaluate(vr, f);
    else
      return mFalseBranch.evaluate(vr, f);
  }

  //-------------------------------------
}
