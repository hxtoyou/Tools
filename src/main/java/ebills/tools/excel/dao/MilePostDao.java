package ebills.tools.excel.dao;

import org.springframework.stereotype.Repository;

import ebills.tools.excel.entity.File.MilePost;
import ebills.tools.system.dao.HibernateDAOImpl;
@Repository
public class MilePostDao extends HibernateDAOImpl<MilePost, Long> {

}
