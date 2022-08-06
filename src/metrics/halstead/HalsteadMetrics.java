package metrics.halstead;

import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class HalsteadMetrics {
	public static ASTVisitorMod parse(char[] str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		IProblem[] problems = cu.getProblems();
		for (IProblem problem : problems) {
			if (problem.getID() == 1610613332)
				continue;
			else if (problem.getID() == 1610613329)
				continue;
			else if (problem.getID() == 1610613328)
				continue;
			else {
				System.out.println("CompilationUnit problem Message " + problem.getMessage() + " \t At line= "
						+ problem.getSourceLineNumber() + "\t Problem ID=" + problem.getID());

				System.out.println("The program will quit now!");
				System.exit(1);
			}
		}
		ASTVisitorMod visitor = new ASTVisitorMod();
		cu.accept(visitor);
		return visitor;
	}

	public int DistOperators, DistOperands, TotOperators, TotOperands;
	private int Vocabulary = 0;
	private int Proglen = 0;
	private double Volume = 0;
	private double Difficulty = 0;
	private double Effort = 0;

	public HalsteadMetrics() {
		DistOperators = 0;
		DistOperands = 0;
		TotOperators = 0;
		TotOperands = 0;
	}

	public void setParameters(int DistOprt, int DistOper, int TotOprt, int TotOper) {
		DistOperators = DistOprt;
		DistOperands = DistOper;
		TotOperators = TotOprt;
		TotOperands = TotOper;
	}

	public int getVocabulary() {
		Vocabulary = DistOperators + DistOperands;
		System.out.println("Vocabulary = " + Vocabulary);
		return Vocabulary;
	}

	public int getProglen() {
		Proglen = TotOperators + TotOperands;
		System.out.println("Program Length = " + Proglen);
		return Proglen;
	}

	public double getVolume() {
		Volume = (TotOperators + TotOperands) * (Math.log(DistOperators + DistOperands) / Math.log(2));
		System.out.println("Volume = " + Volume);
		return Volume;
	}

	public double getDifficulty() {
		Difficulty = (DistOperators / 2) * (TotOperands / (double) DistOperands);//
		System.out.println("Difficulty = " + Difficulty);
		return Difficulty;
	}

	public double getEffort() {
		Effort = ((DistOperators / 2) * (TotOperands / (double) DistOperands))
				* ((TotOperators + TotOperands) * (Math.log(DistOperators + DistOperands) / Math.log(2)));
		System.out.println("Effort = " + Effort);
		return Effort;
	}

	public void psvm(List<String> javaFiles, List<char[]> filesRead) throws IOException {
		ASTVisitorMod ASTVisitorFile = null;
		int DistinctOperators = 0;
		int DistinctOperands = 0;
		int TotalOperators = 0;
		int TotalOperands = 0;
		int OperatorCount = 0;
		int OperandCount = 0;
		for (int i = 0; i < filesRead.size(); i++) {
			ASTVisitorFile = parse(filesRead.get(i));
			DistinctOperators += ASTVisitorFile.oprt.size();
			DistinctOperands += ASTVisitorFile.names.size();
			OperatorCount = 0;
			for (int f : ASTVisitorFile.oprt.values()) {
				OperatorCount += f;
			}
			TotalOperators += OperatorCount;
			OperandCount = 0;
			for (int f : ASTVisitorFile.names.values()) {
				OperandCount += f;
			}
			TotalOperands += OperandCount;
		}
		System.out.print("\n---------------------------------------------");
		System.out.println("\n\tHalstead Complexity Metrics");
		System.out.print("---------------------------------------------\n");
		System.out.print(ASTVisitorFile.names + "\n" + ASTVisitorFile.oprt + "\n");
		System.out.println("Overall Distinct Operators in the directory = " + DistinctOperators);
		System.out.println("Overall Distinct Operands in the directory = " + DistinctOperands);
		System.out.println("Overall Total Operators in the directory = " + TotalOperators);
		System.out.println("Overall Total Operands in the directory = " + TotalOperands);
		setParameters(DistinctOperators, DistinctOperands, TotalOperators, TotalOperands);
		getVocabulary();
		getProglen();
		getVolume();
		getDifficulty();
		getEffort();
	}
}