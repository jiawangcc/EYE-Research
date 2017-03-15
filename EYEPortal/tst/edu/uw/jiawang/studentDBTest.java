package edu.uw.jiawang;

import static org.junit.Assert.*;

import org.junit.Test;

public class studentDBTest {

	@Test
	public void testMatchesKeyword() {
		Student s = new Student();
		s.setStuId(1);
		s.setFirstname("Harry");
		s.setLastname("Potter");
		s.setGrade(4);
		s.setSchool("Bothell Elementary School");
		
		assertTrue(studentDB.matchesKeyword(s, "1"));
		assertTrue(studentDB.matchesKeyword(s, "harry"));
		assertTrue(studentDB.matchesKeyword(s, "Harry"));
		assertTrue(studentDB.matchesKeyword(s, "H Potter"));
		assertTrue(studentDB.matchesKeyword(s, "H P"));
		assertTrue(studentDB.matchesKeyword(s, "H p"));
		assertTrue(studentDB.matchesKeyword(s, "4"));
		assertTrue(studentDB.matchesKeyword(s, "elem"));
		
		assertFalse(studentDB.matchesKeyword(s, "3"));
		assertFalse(studentDB.matchesKeyword(s, "elesm"));
	}

}
