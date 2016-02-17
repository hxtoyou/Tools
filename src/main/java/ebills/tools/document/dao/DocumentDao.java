package ebills.tools.document.dao;

import org.springframework.stereotype.Repository;

import ebills.tools.document.entity.Document;
import ebills.tools.system.dao.HibernateDAOImpl;

@Repository
public class DocumentDao extends HibernateDAOImpl<Document, Long> {

}

