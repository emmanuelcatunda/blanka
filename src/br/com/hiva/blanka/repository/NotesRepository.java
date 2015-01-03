package br.com.hiva.blanka.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.hiva.blanka.model.Note;

public interface NotesRepository extends MongoRepository<Note, String>  {
	
	@Query(value="{ 'tags': { $in:?0} }")
	public List<Note>findNotesbyTag(String... tags);

}