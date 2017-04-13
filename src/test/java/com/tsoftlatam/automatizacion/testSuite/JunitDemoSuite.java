package com.tsoftlatam.automatizacion.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tsoftlatam.automatizacion.test.Example001Test;

@RunWith(Suite.class)
@SuiteClasses({ Example001Test.class })
public class JunitDemoSuite {

}
