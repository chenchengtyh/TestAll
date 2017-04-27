import java.math.BigDecimal;
import java.util.Scanner;

//仅适用于今天国际   author:LXM
public class SalaryCalculator {

	// Part.1 比例部分 Ratio
	static double Ratio_PI = 0.08;// 养老保险（Pension Insurance）
	static double Ratio_MI = 0.02;// 医疗保险（Medical Insurance）
	static double Ratio_HF = 0.05;// 住房公积金（Housing Fund）
	static double Ratio_UI = 0.005;// 失业保险（Unemployment Insurance）

	//Part.2 固定值 Wage
	static double Wage_TT = 3500;// 个税起征点（Tax threshold）
	static double Wage_MS = 2030;//深圳市最低标准工资（ Minimum Standard Wage）
	static double Wage_PI;// 养老金
	static double Wage_MI;// 医保
	static double Wage_HF;// 住房公积金
	static double Wage_UI;// 失业保险

	//Part.3 工资值 Value
	static double Value_RW;// 实发工资（Real wages）
	static double Value_BW;// 基本工资（Basic Wage）
	static double Value_PP;// 绩效工资（Performance Pay）
	static double Value_Bonus;// 额外津贴（Bonus）
	static double Value_PIT = 0;// 个人所得税（Personal Income Tax）


	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("请输入您的基本工资：(基本工资为税前工资的80%)");
		try {
			Value_BW = Double.parseDouble(scan.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("输入错误！");
			scan.close();
			return;
		}

		System.out.println("请输入您的绩效工资：");
		try {
			Value_PP = Double.parseDouble(scan.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("输入错误！");
			scan.close();
			return;
		}

		System.out.println("请输入您的额外津贴：(全勤、高温补贴等)");
		try {
			Value_Bonus = Double.parseDouble(scan.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("输入错误！");
			scan.close();
			return;
		}

		scan.close();
		Wage_PI = Value_BW*1.25 * Ratio_PI;
		Wage_MI = Value_BW*1.25 * Ratio_MI;
		Wage_HF = Value_BW*1.25 * Ratio_HF;
		Wage_UI = Wage_MS * Ratio_UI;
		Value_Bonus += (Value_PP > Value_BW * 0.25) ? (Value_PP - Value_BW * 0.25) : 0;
		Value_PIT = getPIT(Value_BW*1.25 - Wage_PI - Wage_MI - Wage_HF - Wage_UI - Wage_TT);
		Value_RW = Value_BW*1.25 - Wage_PI - Wage_MI - Wage_HF - Wage_UI - Value_PIT + Value_Bonus;
		System.out.println("您的实发工资为" + Keep2Decimal(Value_RW) + "元，养老保险个人缴" + Keep2Decimal(Wage_PI) + "元，医疗保险个人缴"
				+ Keep2Decimal(Wage_MI) + "元，住房公积金个人缴" + Keep2Decimal(Wage_HF) + "元，失业保险个人缴" + Wage_UI + "元，个人所得税缴"
				+ Keep2Decimal(Value_PIT) + "元，乱扣费" + Keep2Decimal(Value_Bonus*0.1) + "元\n" +
						"备注：若数据有出入，可能五险一金缴费基数为调薪前工资\n" +
						"计算公式：\n" +
						"正常工资 = 基本工资（占80%） + 正常绩效（占20%）\n" +
						"养老保险 = 正常工资 * 8%\n" +
						"医疗保险 = 正常工资 * 2%\n" +
						"住房公积金 = 正常工资 * 5%\n" +
						"失业保险 = 最低标准工资 * 0.5%\n" +
						"个人所得税 = （正常工资 - 养老 - 医疗 - 公积金 - 失业保险 - 个税起征点）*比例\n" +
						"额外绩效 = 实发绩效工资 - 正常绩效\n" +
						"实发工资 = 正常工资-养老-医保-公积金-失业保险-个税+(全勤+高温补贴+额外绩效等奖金)*0.9");
	}

	private static double getPIT(double taxable_income) {
		if (taxable_income <= 0) {
			return 0;
		} else if (taxable_income <= 1500) {
			return taxable_income * 0.03;
		} else if (taxable_income <= 4500) {
			return taxable_income * 0.1 - 105;
		} else if (taxable_income <= 9000) {
			return taxable_income * 0.2 - 555;
		} else if (taxable_income <= 35000) {
			return taxable_income * 0.25 - 1005;
		} else if (taxable_income <= 55000) {
			return taxable_income * 0.3 - 2755;
		} else if (taxable_income <= 80000) {
			return taxable_income * 0.35 - 5505;
		} else {
			return taxable_income * 0.45 - 13505;
		}
	}

	private static double Keep2Decimal(double value) {
		value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return value;
	}
}
