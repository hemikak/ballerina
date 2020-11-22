/*
 * Copyright (c) 2020, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.langserver.completions.providers.context;

import io.ballerina.compiler.api.symbols.Symbol;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.NonTerminalNode;
import io.ballerina.compiler.syntax.tree.ObjectConstructorExpressionNode;
import io.ballerina.compiler.syntax.tree.QualifiedNameReferenceNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.compiler.syntax.tree.Token;
import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.langserver.common.utils.SymbolUtil;
import org.ballerinalang.langserver.common.utils.completion.QNameReferenceUtil;
import org.ballerinalang.langserver.commons.CompletionContext;
import org.ballerinalang.langserver.commons.completion.LSCompletionItem;
import org.ballerinalang.langserver.completions.SnippetCompletionItem;
import org.ballerinalang.langserver.completions.providers.AbstractCompletionProvider;
import org.ballerinalang.langserver.completions.util.Snippet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Completion provider for {@link ObjectConstructorExpressionNode} context.
 *
 * @since 2.0.0
 */
@JavaSPIService("org.ballerinalang.langserver.commons.completion.spi.CompletionProvider")
public class ObjectConstructorExpressionNodeContext
        extends AbstractCompletionProvider<ObjectConstructorExpressionNode> {

    public ObjectConstructorExpressionNodeContext() {
        super(ObjectConstructorExpressionNode.class);
    }

    @Override
    public List<LSCompletionItem> getCompletions(CompletionContext context, ObjectConstructorExpressionNode node) {
        if (this.onSuggestObjectOnly(context, node)) {
            return Arrays.asList(
                    new SnippetCompletionItem(context, Snippet.KW_OBJECT.get()),
                    new SnippetCompletionItem(context, Snippet.EXPR_OBJECT_CONSTRUCTOR.get())
            );
        }
        if (this.onSuggestTypeReferences(context, node)) {
            return this.getTypeReferenceCompletions(context);
        }

        return this.getConstructorBodyCompletions(context);
    }

    private List<LSCompletionItem> getTypeReferenceCompletions(CompletionContext ctx) {
        NonTerminalNode nodeAtCursor = ctx.getNodeAtCursor();
        if (nodeAtCursor.kind() == SyntaxKind.QUALIFIED_NAME_REFERENCE) {
            QualifiedNameReferenceNode qNameRef = (QualifiedNameReferenceNode) nodeAtCursor;
            Predicate<Symbol> predicate = SymbolUtil::isObject;
            return this.getCompletionItemList(QNameReferenceUtil.getModuleContent(ctx, qNameRef, predicate), ctx);
        }
        List<Symbol> visibleSymbols = ctx.getVisibleSymbols(ctx.getCursorPosition());
        List<Symbol> objectEntries = visibleSymbols.stream()
                .filter(SymbolUtil::isObject)
                .collect(Collectors.toList());

        List<LSCompletionItem> completionItems = this.getCompletionItemList(objectEntries, ctx);
        completionItems.addAll(this.getModuleCompletionItems(ctx));

        return completionItems;
    }

    private List<LSCompletionItem> getConstructorBodyCompletions(CompletionContext context) {
        List<LSCompletionItem> completionItems = new ArrayList<>();

        completionItems.add(new SnippetCompletionItem(context, Snippet.KW_PRIVATE.get()));
        completionItems.add(new SnippetCompletionItem(context, Snippet.KW_PUBLIC.get()));
        completionItems.add(new SnippetCompletionItem(context, Snippet.KW_FINAL.get()));
        completionItems.add(new SnippetCompletionItem(context, Snippet.KW_REMOTE.get()));
        completionItems.add(new SnippetCompletionItem(context, Snippet.DEF_REMOTE_FUNCTION.get()));
        completionItems.add(new SnippetCompletionItem(context, Snippet.DEF_FUNCTION.get()));
        completionItems.add(new SnippetCompletionItem(context, Snippet.KW_FUNCTION.get()));
        completionItems.addAll(this.getTypeItems(context));
        completionItems.addAll(this.getModuleCompletionItems(context));

        return completionItems;
    }

    private boolean onSuggestObjectOnly(CompletionContext context, ObjectConstructorExpressionNode node) {
        int cursor = context.getCursorPositionInTree();
        NodeList<Token> qualifiers = node.objectTypeQualifiers();

        if (qualifiers.isEmpty()) {
            return false;
        }
        Token objectKeyword = node.objectKeyword();
        Token lastQualifier = qualifiers.get(qualifiers.size() - 1);

        return cursor > lastQualifier.textRange().endOffset()
                && (objectKeyword.isMissing() || cursor < objectKeyword.textRange().startOffset());
    }

    private boolean onSuggestTypeReferences(CompletionContext context, ObjectConstructorExpressionNode node) {
        int cursor = context.getCursorPositionInTree();
        Token objectKeyword = node.objectKeyword();
        Token openBrace = node.openBraceToken();

        return !objectKeyword.isMissing() && cursor > objectKeyword.textRange().startOffset()
                && (openBrace.isMissing() || cursor < openBrace.textRange().endOffset());
    }
}