package com.fortech;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.fortech.controller.account.CreateAccountIT;
import com.fortech.controller.account.DeleteAccountIT;
import com.fortech.controller.account.GetAccountCartDetailsIT;
import com.fortech.controller.account.GetCustomersIT;
import com.fortech.controller.account.GetOrdersIT;
import com.fortech.controller.account.LoginIT;
import com.fortech.controller.account.PlaceOrderIT;
import com.fortech.controller.account.RemoveItemFromCartIT;
import com.fortech.controller.cart.ChangeOrderStatusIT;
import com.fortech.controller.vinylController.AddVinylToCartIT;
import com.fortech.controller.vinylController.DeleteVinylIT;
import com.fortech.controller.vinylController.GetAllVinylsDetailsIT;
import com.fortech.controller.vinylController.GetInventoryIT;
import com.fortech.controller.vinylController.GetVinylDetailsIT;
import com.fortech.controller.vinylController.UpdateVinylInfoIT;
import com.fortech.controller.vinylController.VinylCreateIT;

@RunWith(Suite.class)
@SuiteClasses({ CreateAccountIT.class, DeleteAccountIT.class, 
	GetAccountCartDetailsIT.class, GetCustomersIT.class, GetOrdersIT.class, 
	LoginIT.class, PlaceOrderIT.class,ChangeOrderStatusIT.class, AddVinylToCartIT.class, DeleteVinylIT.class, 
	GetAllVinylsDetailsIT.class, UpdateVinylInfoIT.class, VinylCreateIT.class,GetInventoryIT.class,GetVinylDetailsIT.class,RemoveItemFromCartIT.class })
public class AllTests {

}
