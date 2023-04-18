package com.xyz.online.customDB;

import java.util.ArrayList;
import java.util.List;

import com.xyz.online.entities.Show;
import com.xyz.online.entities.ShowType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class BuildShows {
	
	public static volatile List<Show> shows = new ArrayList<>();
	public static Show dummyShow = null;
	
	public static void buildShows() {
		Show showRRR = new Show("RRR", ShowType.MOVIE, 182);
		Show showTKF = new Show("TKF -THE SHOW", ShowType.MOVIE, 170);
		Show showAttack = new Show("Attack", ShowType.MOVIE, 105);
		Show showTheLess = new Show("The LION LESSON", ShowType.THEATRE, 70);
		Show showMagic = new Show("Magic of West", ShowType.MOVIE, 132);
		
		shows.add(showRRR);
		shows.add(showTKF);
		shows.add(showAttack);
		shows.add(showTheLess);
		shows.add(showMagic);
	}
	
	public static Show getDummyShow() {
		if(dummyShow != null)
			return dummyShow;
		dummyShow = new Show("dummy", ShowType.DUMMY, 0);
		return dummyShow;
	}
	
	public static boolean registerShow(Show show) {
		boolean isPresent = shows.parallelStream().anyMatch(sh -> (StringUtils.hasText(show.getShowID())) &&
				sh.getShowID().equals(show.getShowID())
				&& sh.getShowName().equalsIgnoreCase(show.getShowName()));
		if(isPresent)
			shows.add(show);
		return !isPresent;
	}

}
