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

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.ballerina.plugins.idea.psi.BallerinaAnnotationAttachment;
import io.ballerina.plugins.idea.psi.BallerinaCallableUnitBody;
import io.ballerina.plugins.idea.psi.BallerinaDocumentationString;
import io.ballerina.plugins.idea.psi.BallerinaObjectInitializer;
import io.ballerina.plugins.idea.psi.BallerinaObjectInitializerParameterList;
import io.ballerina.plugins.idea.psi.BallerinaVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.ballerina.plugins.idea.psi.BallerinaTypes.NEW;
import static io.ballerina.plugins.idea.psi.BallerinaTypes.PUBLIC;

public class BallerinaObjectInitializerImpl extends BallerinaCompositeElementImpl implements BallerinaObjectInitializer {

  public BallerinaObjectInitializerImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull BallerinaVisitor visitor) {
    visitor.visitObjectInitializer(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BallerinaVisitor) accept((BallerinaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<BallerinaAnnotationAttachment> getAnnotationAttachmentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, BallerinaAnnotationAttachment.class);
  }

  @Override
  @Nullable
  public BallerinaCallableUnitBody getCallableUnitBody() {
    return PsiTreeUtil.getChildOfType(this, BallerinaCallableUnitBody.class);
  }

  @Override
  @Nullable
  public BallerinaObjectInitializerParameterList getObjectInitializerParameterList() {
    return PsiTreeUtil.getChildOfType(this, BallerinaObjectInitializerParameterList.class);
  }

  @Override
  @Nullable
  public BallerinaDocumentationString getDocumentationString() {
    return PsiTreeUtil.getChildOfType(this, BallerinaDocumentationString.class);
  }

  @Override
  @NotNull
  public PsiElement getNew() {
    return notNullChild(findChildByType(NEW));
  }

  @Override
  @Nullable
  public PsiElement getPublic() {
    return findChildByType(PUBLIC);
  }

}
