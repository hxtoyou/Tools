package ebills.tools.excel.dao;

import org.springframework.stereotype.Repository;

import ebills.tools.excel.entity.File.Project;
import ebills.tools.system.dao.HibernateDAOImpl;
@Repository
public class ProjectDao extends HibernateDAOImpl<Project, Long> {

}
