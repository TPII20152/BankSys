package test.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountVectorTest.class, SQLiteAccountsTest.class })
public class PersistenceTests {

}
