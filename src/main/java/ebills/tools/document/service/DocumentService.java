package ebills.tools.document.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebills.tools.document.dao.DocumentDao;
import ebills.tools.document.entity.Document;
import ebills.tools.system.dao.HibernateDAO;
import ebills.tools.system.service.HibernateDataServiceImpl;

@Service
public class DocumentService extends HibernateDataServiceImpl<Document, Long> {
	@Autowired
	private DocumentDao documentDAO;

	@Override
	public HibernateDAO<Document, Long> getDAO() {
		return documentDAO;
	}
}