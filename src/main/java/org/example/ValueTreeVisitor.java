package org.example;

import com.carstenGrammar.CarstenParser;
import com.carstenGrammar.CarstenVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.example.AshnodSetup.AshnodSetup;
import org.example.AshnodSetup.InitialVariables;
import org.example.ValueTree.*;

import java.util.ArrayList;
import java.util.List;

public class ValueTreeVisitor implements CarstenVisitor {
    public RuleBlock visitMatcherBlock(CarstenParser.MatcherBlockContext ctx) {
        List<AssignmentNode> actions = new ArrayList<>();
        for (CarstenParser.ActionLineContext actionLine: ctx.actionBlock().actionLine()) {
            actions.add(this.visitActionLine(actionLine));
        }
        return new RuleBlock(ctx.RULE().toString(), actions);
    }

    @Override
    public AshnodSetup visitMatcherFile(CarstenParser.MatcherFileContext ctx) {
        ArrayList<RuleBlock> rules = new ArrayList<>();
        InitialVariables initialVariables = new InitialVariables();
        for (CarstenParser.MatcherRecordContext recordContext : ctx.matcherRecord()) {
            if (recordContext.actionLine() != null) {
                System.out.println("Action block");
                // Here we can process top-level assignments
            }
            if (recordContext.matcherBlock() != null) {
                rules.add(this.visitMatcherBlock(recordContext.matcherBlock()));
            }
        }
        RuleFile ruleFile = new RuleFile(rules);

        return new AshnodSetup(ruleFile, initialVariables);
    }

    @Override
    public Object visitActionBlock(CarstenParser.ActionBlockContext ctx) {
        return null;
    }

    private ValueNode visitExpression(CarstenParser.ExprContext ctx) {
        if (ctx instanceof CarstenParser.InfixExprContext) {
            return this.visitInfixExpr((CarstenParser.InfixExprContext) ctx);
        }
        if (ctx instanceof CarstenParser.NumberExprContext) {
            return this.visitNumberExpr((CarstenParser.NumberExprContext) ctx);
        }
        if (ctx instanceof CarstenParser.FuncExprContext) {
            return this.visitFuncExpr((CarstenParser.FuncExprContext) ctx);
        }
        if (ctx instanceof CarstenParser.ParensExprContext) {
            return this.visitParensExpr((CarstenParser.ParensExprContext) ctx);
        }

        System.out.println("Found some fuckery");
        return new MultiplicationNode(new NumberNode(1), new NumberNode(2));
    }
    @Override
    public AssignmentNode visitActionLine(CarstenParser.ActionLineContext ctx) {
        return new AssignmentNode(ctx.variable.getText(), this.visitExpression(ctx.expr()));
    }

    @Override
    public ValueNode visitInfixExpr(CarstenParser.InfixExprContext ctx) {
        switch (ctx.op.getType()) {
            case CarstenParser.OP_ADD: {
                return new AdditionNode(this.visitExpression(ctx.left), this.visitExpression(ctx.right));
            }
            case CarstenParser.OP_SUB: {
                return new SubtractionNode(this.visitExpression(ctx.left), this.visitExpression(ctx.right));
            }
            case CarstenParser.OP_MUL: {
                return new MultiplicationNode(this.visitExpression(ctx.left), this.visitExpression(ctx.right));
            }
            case CarstenParser.OP_DIV:
            default: {
                return new DivisionNode(this.visitExpression(ctx.left), this.visitExpression(ctx.right));
            }
        }
    }

    @Override
    public Object visitUnaryExpr(CarstenParser.UnaryExprContext ctx) {
        return null;
    }

    @Override
    public Object visitMatcherRecord(CarstenParser.MatcherRecordContext ctx) {
        System.out.println("Visiting matcher record");
        return null;
    }

    @Override
    public ValueNode visitFuncExpr(CarstenParser.FuncExprContext ctx) {
        ValueNode[] args = new ValueNode[]{
            this.visitExpression(ctx.expr())
        };
        return new FunctionCallNode(ctx.func.getText(), args);
    }

    @Override
    public ValueNode visitNumberExpr(CarstenParser.NumberExprContext ctx) {
        return this.visitAtom(ctx.atom());
    }

    @Override
    public ValueNode visitParensExpr(CarstenParser.ParensExprContext ctx) {
        return this.visitExpression(ctx.expr());
    }

    @Override
    public ValueNode visitAtom(CarstenParser.AtomContext ctx) {
        if (ctx.value != null) {
            return new NumberNode(Integer.valueOf(ctx.value.getText()));
        }
        return new VariableNode(ctx.variable.getText());
    }

    @Override
    public Object visitCompareSymbol(CarstenParser.CompareSymbolContext ctx) {
        return null;
    }

    @Override
    public Object visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public Object visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Object visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Object visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
