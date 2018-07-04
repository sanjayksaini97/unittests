package com.insta.tests;

import static org.testng.Assert.fail;

import org.easymock.EasyMock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.insta.java.CalcMethodException;
import com.insta.java.ICalcMethod;
import com.insta.java.IncomeCalculator;
import com.insta.java.Position;
import com.insta.java.PositionException;
import com.insta.reporter.ExtentTestNGReportBuilder;

public class IncomeCalculatorTest extends ExtentTestNGReportBuilder {

	private ICalcMethod calcMethod;
	private IncomeCalculator calc;

	@BeforeMethod
	public void setUp() throws Exception {
		calcMethod = EasyMock.createMock(ICalcMethod.class);
		calc = new IncomeCalculator();
	}

	@Test
	public void testCalc1() {
		// Setting up the expected value of the method call calc
		EasyMock.expect(calcMethod.calc(Position.BOSS)).andReturn(70000.0)
				.times(2);
		EasyMock.expect(calcMethod.calc(Position.PROGRAMMER))
				.andReturn(50000.0);
		// Setup is finished need to activate the mock
		EasyMock.replay(calcMethod);

		calc.setCalcMethod(calcMethod);
		try {
			calc.calc();
			fail("Exception did not occur");
		} catch (PositionException e) {

		}
		calc.setPosition(Position.BOSS);
		assert (70000.0 == calc.calc());
		assert (70000.0 == calc.calc());
		calc.setPosition(Position.PROGRAMMER);
		assert (50000.0 == calc.calc());
		calc.setPosition(Position.SURFER);
		EasyMock.verify(calcMethod);
	}

	@Test(expectedExceptions = CalcMethodException.class)
	public void testNoCalc() {
		calc.setPosition(Position.SURFER);
		calc.calc();
	}

	@Test(expectedExceptions = PositionException.class)
	public void testNoPosition() {
		EasyMock.expect(calcMethod.calc(Position.BOSS)).andReturn(70000.0);
		EasyMock.replay(calcMethod);
		calc.setCalcMethod(calcMethod);
		calc.calc();
	}

	@Test(expectedExceptions = PositionException.class)
	public void testCalc2() {
		// Setting up the expected value of the method call calc
		EasyMock.expect(calcMethod.calc(Position.SURFER)).andThrow(
				new PositionException("Don't know this guy")).times(1);

		// Setup is finished need to activate the mock
		EasyMock.replay(calcMethod);
		calc.setPosition(Position.SURFER);
		calc.setCalcMethod(calcMethod);
		calc.calc();
	}

}
