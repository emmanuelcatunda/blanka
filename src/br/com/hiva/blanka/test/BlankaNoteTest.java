package br.com.hiva.blanka.test;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.hiva.blanka.config.MongoDbConfig;
import br.com.hiva.blanka.model.Note;
import br.com.hiva.blanka.repository.NotesRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoDbConfig.class })
public class BlankaNoteTest {

	@Autowired
	NotesRepository repository;

	@Test
	public void saveEditAndSearchBytagSimpleNote() {

		long count = repository.count();

		Note note = repository.save(new Note("test note", "bla bla bla ", "1testTag", "anotherTag"));

		note.addTag("lastTag");
		repository.save(note);

		Assert.assertEquals(1, repository.findNotesbyTag("lastTag").size());
		repository.delete(note);

		Assert.assertEquals(count, repository.count());
	}

	@Test(expected = ConstraintViolationException.class)
	public void validationTest() {
		long count = repository.count();
		Note note = repository.save(new Note("test note2", "more and more notes", "moreuselesstags", "anotherTag"));
		try {
			
			note.setTitle("");
			repository.save(note);
			
		} 
		finally {
			repository.delete(note);
			Assert.assertEquals(count, repository.count());
		}
	}
}