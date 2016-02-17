package ebills.tools.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebills.tools.excel.dao.InvoiceDao;
import ebills.tools.excel.entity.File.Invoice;
import ebills.tools.system.dao.HibernateDAO;
import ebills.tools.system.service.HibernateDataServiceImpl;
@Service
public class InvoiceService  extends HibernateDataServiceImpl<Invoice, Long> {
	@Autowired
	private InvoiceDao invoiceDao;
	@Override
	public HibernateDAO<Invoice, Long> getDAO() {
		// TODO Auto-generated method stub
		return invoiceDao;
	}

}
