package ebills.tools.excel.dao;

import org.springframework.stereotype.Repository;

import ebills.tools.excel.entity.File.Collection;
import ebills.tools.system.dao.HibernateDAOImpl;
@Repository
public class CollectionDao extends HibernateDAOImpl<Collection, Long> {

}
