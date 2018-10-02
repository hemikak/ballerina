/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

// This is a generated file. Not intended for manual editing.
package io.ballerina.plugins.idea.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static io.ballerina.plugins.idea.psi.BallerinaTypes.*;
import io.ballerina.plugins.idea.psi.*;

public class BallerinaLambdaFunctionImpl extends BallerinaCompositeElementImpl implements BallerinaLambdaFunction {

  public BallerinaLambdaFunctionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull BallerinaVisitor visitor) {
    visitor.visitLambdaFunction(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BallerinaVisitor) accept((BallerinaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public BallerinaCallableUnitBody getCallableUnitBody() {
    return PsiTreeUtil.getChildOfType(this, BallerinaCallableUnitBody.class);
  }

  @Override
  @Nullable
  public BallerinaFormalParameterList getFormalParameterList() {
    return PsiTreeUtil.getChildOfType(this, BallerinaFormalParameterList.class);
  }

  @Override
  @Nullable
  public BallerinaLambdaReturnParameter getLambdaReturnParameter() {
    return PsiTreeUtil.getChildOfType(this, BallerinaLambdaReturnParameter.class);
  }

  @Override
  @Nullable
  public PsiElement getLeftParenthesis() {
    return findChildByType(LEFT_PARENTHESIS);
  }

  @Override
  @Nullable
  public PsiElement getRightParenthesis() {
    return findChildByType(RIGHT_PARENTHESIS);
  }

  @Override
  @NotNull
  public PsiElement getFunction() {
    return notNullChild(findChildByType(FUNCTION));
  }

  @Override
  @Nullable
  public PsiElement getReturns() {
    return findChildByType(RETURNS);
  }

}
