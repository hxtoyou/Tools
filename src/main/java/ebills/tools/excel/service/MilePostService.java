package ebills.tools.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebills.tools.excel.dao.MilePostDao;
import ebills.tools.excel.entity.File.MilePost;
import ebills.tools.system.dao.HibernateDAO;
import ebills.tools.system.service.HibernateDataServiceImpl;
@Service
public class MilePostService  extends HibernateDataServiceImpl<MilePost, Long> {
	@Autowired
	private MilePostDao milePostDao;
	@Override
	public HibernateDAO<MilePost, Long> getDAO() {
		// TODO Auto-generated method stub
		return milePostDao;
	}

}
