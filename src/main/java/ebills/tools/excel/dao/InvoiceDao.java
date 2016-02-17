package ebills.tools.excel.dao;

import org.springframework.stereotype.Repository;

import ebills.tools.excel.entity.File.Invoice;
import ebills.tools.system.dao.HibernateDAOImpl;
@Repository
public class InvoiceDao extends HibernateDAOImpl<Invoice, Long> {

}
