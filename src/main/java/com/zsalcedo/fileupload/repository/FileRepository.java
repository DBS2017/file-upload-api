package com.zsalcedo.fileupload.repository;

import com.zsalcedo.fileupload.model.File;
import com.zsalcedo.fileupload.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {


}
