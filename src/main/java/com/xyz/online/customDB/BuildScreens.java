package com.xyz.online.customDB;

import java.util.ArrayList;
import java.util.List;

import com.xyz.online.entities.Screen;
import org.springframework.stereotype.Repository;


@Repository
public class BuildScreens {
	
	public static List<Screen> screens = new ArrayList<>();
	
	public static void buildScreens() {
		Screen screen1 = new Screen(10, 30);
		Screen screen2 = new Screen(15, 35);
		Screen screen3 = new Screen(12, 30);
		Screen screen4 = new Screen(16, 40);
		Screen screen5 = new Screen(11, 25);
		Screen screen6 = new Screen(10, 28);
		Screen screen7 = new Screen(15, 25);
		Screen screen8 = new Screen(13, 33);
		Screen screen9 = new Screen(12, 40);
		Screen screen10 = new Screen(12, 26);
		
		screens.add(screen1);
		screens.add(screen2);
		screens.add(screen3);
		screens.add(screen4);
		screens.add(screen5);
		screens.add(screen6);
		screens.add(screen7);
		screens.add(screen8);
		screens.add(screen9);
		screens.add(screen10);
	}
	

}
