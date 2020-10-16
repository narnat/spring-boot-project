package com.farrukh.spring.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.farrukh.spring.demo.model.Person;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

	private static List<Person> DB = new ArrayList<Person>();
	
	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return DB.stream().
				filter(person -> person.getId().equals(id)).
				findFirst();
	}

	@Override
	public int deletePersonById(UUID id) {
		Optional<Person> personMaybe = selectPersonById(id);
		
		if (personMaybe.isEmpty()) {
			return 0;
		}
		DB.remove(personMaybe.get());
		return 1;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		return selectPersonById(id)
				.map(p -> {
					int indexOfPersonToDelete = DB.indexOf(p);
					if (indexOfPersonToDelete >= 0) {
						DB.set(indexOfPersonToDelete, new Person(id, person.getName()));
						return 1;
					}
					return 0;
				})
				.orElse(0);
	}
	
	
}