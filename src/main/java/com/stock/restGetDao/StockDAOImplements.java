package com.stock.restGetDao;

import java.security.Principal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.stock.dao.StockIndex;
import com.stock.dao.StockPortfolio;
import com.stock.dao.StockWatchlist;
 
import org.springframework.stereotype.Repository;

import com.stock.dao.StockIndex;
import com.stock.thread.portfolio.PortfolioDao;
import com.stock.thread.watchlist.WatchlistDao;

import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

@Repository
public class StockDAOImplements implements StockDao{
 
	
//starting session

	@Autowired
private WatchlistDao watchlistDao;
	
	@Autowired
private PortfolioDao portfolioDao;

	@Override
	public List<StockIndex> getQuotes() {

		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
					.addAnnotatedClass(StockIndex.class)
						.buildSessionFactory();
		Session session =factory.getCurrentSession();
		session.beginTransaction();
		
		List<StockIndex> quote =session.createQuery("from StockIndex").getResultList();
		System.out.println(quote);
		System.out.println("*******************************************************");
		
		session.getTransaction().commit();
		
		
		factory.close();
		return quote;
	}

	@Override
	public StockIndex getQuote(String ticker) {

		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
					.addAnnotatedClass(StockIndex.class)
						.buildSessionFactory();
		
		Session session =factory.getCurrentSession();
		session.beginTransaction();
		
		StockIndex theStock =session.get(StockIndex.class, ticker);		
		
		session.getTransaction().commit();
		
		
		
		factory.close();
		return  theStock;
		
	}

	

	@Override
	public Quote getQuoteDetails(String ticker) {
		// TODO Auto-generated method stub
		



		final IEXTradingClient iexTradingClient = IEXTradingClient.create();
		
		Quote quote ;
			quote = iexTradingClient.executeRequest(new QuoteRequestBuilder()
					        .withSymbol(ticker)
					        .build());
			      
		    
	     return quote;
		
		
		
		
	}
//watchlist
	@Override
	public List<com.stock.dao.StockWatchlist> createQuoteWatchlist(String ticker,Principal pri) {
		
	
	return watchlistDao.createQuoteWatchlist(ticker,pri);
 
	}

	@Override
	public List<StockWatchlist> getQuoteWatchlist(Principal pri) {
		// TODO Auto-generated method stub
		return watchlistDao.getQuoteWatchlist(pri);
 
	}

	
//portfolio
	@Override
	public List<StockPortfolio> createQuotePortfolio(String ticker, Principal pri, int quantity,int date) {
		// TODO Auto-generated method stub
		return portfolioDao.createQuotePortfolio(ticker,pri,quantity,date);
		
	}

	@Override
	public List<StockPortfolio> getQuotePortfolio(Principal pri) {
		// TODO Auto-generated method stub
		return portfolioDao.getQuotePortfolio(pri);
		 
	}
}
