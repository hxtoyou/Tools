package ebills.tools.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebills.tools.excel.dao.ProjectDao;
import ebills.tools.excel.entity.File.Project;
import ebills.tools.system.dao.HibernateDAO;
import ebills.tools.system.service.HibernateDataServiceImpl;
@Service
public class ProjectService  extends HibernateDataServiceImpl<Project, Long> {
	@Autowired
	private ProjectDao projectDao;
	@Override
	public HibernateDAO<Project, Long> getDAO() {
		// TODO Auto-generated method stub
		return projectDao;
	}

}
