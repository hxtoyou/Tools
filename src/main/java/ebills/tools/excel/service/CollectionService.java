package ebills.tools.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebills.tools.excel.dao.CollectionDao;
import ebills.tools.excel.entity.File.Collection;
import ebills.tools.system.dao.HibernateDAO;
import ebills.tools.system.service.HibernateDataServiceImpl;
@Service
public class CollectionService extends HibernateDataServiceImpl<Collection, Long> {
	@Autowired
	private CollectionDao collectionDao;
	@Override
	public HibernateDAO<Collection, Long> getDAO() {
		// TODO Auto-generated method stub
		return collectionDao;
	}

}
