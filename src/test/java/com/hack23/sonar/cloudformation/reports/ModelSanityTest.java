/*
 * Cloudformation Plugin for SonarQube
 * Copyright (C) 2019 James Pether Sörling
 * james@hack23.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.hack23.sonar.cloudformation.reports;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hack23.sonar.cloudformation.reports.cfnnag.CfnNagScanReport;
import com.hack23.sonar.cloudformation.reports.checkov.CheckovReport;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.EqualsAndHashCodeMatchRule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * The Class ModelSanityTest.
 */
public final class ModelSanityTest extends Assert {

	/** The Constant FilterPackageInfo. */
	private static final FilterPackageInfo FilterPackageInfo = new FilterPackageInfo();

	/** The Constant EXPECT_CLASSES_IN_PACKAGE. */
	private static final String EXPECT_CLASSES_IN_PACKAGE = "Expect classes in package";


	/**
	 * Check cfn nag report package test.
	 */
	@Test
	public void checkCfnNagReportPackageTest() {
		assertTrue(EXPECT_CLASSES_IN_PACKAGE,
				checkAllClassesInPackage(CfnNagScanReport.class.getPackage().getName()));

	}

	/**
	 * Check checkov report package test.
	 */
	@Test
	public void checkCheckovReportPackageTest() {
		assertTrue(EXPECT_CLASSES_IN_PACKAGE,
				checkAllClassesInPackage(CheckovReport.class.getPackage().getName()));

	}

	/**
	 * Check all classes in package.
	 *
	 * @param string the string
	 * @return true, if successful
	 */
	protected boolean checkAllClassesInPackage(final String string) {
		final List<PojoClass> pojoClassesRecursively = PojoClassFactory.getPojoClassesRecursively(string,
				new FilterTestClasses());

		final Validator validator = ValidatorBuilder.create().with(new SetterMustExistRule(), new GetterMustExistRule())
				.with(new SetterTester(), new GetterTester()).with(new InvokeToStringTester())
				.with(new InvokeHashcodeTester()).with(new DummyEqualsTester()).with(new EqualsAndHashCodeMatchRule()).build();
		validator.validate(pojoClassesRecursively);


		return true;
	}

	/**
	 * The Class FilterTestClasses.
	 */
	private static class FilterTestClasses implements PojoClassFilter {

		/**
		 * Include.
		 *
		 * @param pojoClass the pojo class
		 * @return true, if successful
		 */
		@Override
		public boolean include(final PojoClass pojoClass) {
			return (!pojoClass.getSourcePath().contains("/test-classes/") && !pojoClass.getClazz().getName().contains("_") && !pojoClass.isEnum() && !pojoClass.isAbstract())
					&& FilterPackageInfo.include(pojoClass);
		}
	}

	/**
	 * The Class InvokeToStringTester.
	 */
	private static class InvokeToStringTester implements Tester {

		/**
		 * Run.
		 *
		 * @param pojoClass the pojo class
		 */
		@Override
		public void run(final PojoClass pojoClass) {
			final Object instance = RandomFactory.getRandomValue(pojoClass.getClazz());
			Affirm.affirmNotNull("toStringFailure", instance.toString());
		}
	}

	/**
	 * The Class InvokeHashcodeTester.
	 */
	private static class InvokeHashcodeTester implements Tester {

		/**
		 * Run.
		 *
		 * @param pojoClass the pojo class
		 */
		@Override
		public void run(final PojoClass pojoClass) {
			final Object instance = RandomFactory.getRandomValue(pojoClass.getClazz());
			Affirm.affirmFalse("hashCodeFailure", 0 == instance.hashCode());
		}
	}

	/**
	 * The Class DummyEqualsTester.
	 */
	private static class DummyEqualsTester implements Tester {

		/**
		 * Run.
		 *
		 * @param pojoClass the pojo class
		 */
		@Override
		public void run(final PojoClass pojoClass) {
			final Object instance = randomValues(pojoClass);

			Affirm.affirmFalse("EqualsCompareNullFailure", instance.equals(null));
			Affirm.affirmFalse("EqualsCompareWrongClassFailure", "WrongClass".equals(instance));
			Affirm.affirmTrue("EqualsCompareSelfFailure", instance.equals(instance));

			final Object instance2 = randomValues(pojoClass);

			instance.equals(instance2);
		}

		/**
		 * Random values.
		 *
		 * @param pojoClass the pojo class
		 * @return the object
		 */
		private Object randomValues(final PojoClass pojoClass) {
			final Object instance = RandomFactory.getRandomValue(pojoClass.getClazz());
			randomValues(instance, pojoClass);

			return instance;
		}

		/**
		 * Random values.
		 *
		 * @param instance the instance
		 * @param pojoClass the pojo class
		 */
		private static void randomValues(final Object instance, final PojoClass pojoClass) {
			if (pojoClass == null) {
				return;
			}

			for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
				if (fieldEntry.hasSetter()) {
					final Object value;

					value = RandomFactory.getRandomValue(fieldEntry);
					fieldEntry.invokeSetter(instance, value);
				}
			}
			randomValues(instance, pojoClass.getSuperClass());
		}
	}

}
