package com.demo.resources;

import org.openqa.selenium.By;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngularModel;

/* This enum is to store xpath's of the elements */
public enum ObjectRepo  {
	
	TextBox_First(ByAngular.model("first"), "Text Box 1"),
	TextBox_Second(ByAngular.model("second"), "Text Box 2"),
	Button_Go(ByAngular.buttonText("Go!"), "Button - Go"),
	Text_Result(ByAngularModel.className("ng-binding"),"Result"),
	DropDown(ByAngular.model("operator"),"Drop Down"),
	
	TextBox_UserNameBW(By.cssSelector("input#loginform-username"), "User Name"),
	TextBox_PasswordBW(By.cssSelector("input#loginform-password"), "Password"),
	Button_SignInBW(By.cssSelector("button.btn.btn-primary.block"), "SignIn Button"),
	Text_MyCashBW(By.xpath("//div[text()='My Cash Balance']"), "My Cash Balance"),
	Text_BalanceBW(By.xpath("(//div[text()='My Cash Balance']/following-sibling::div)[1]"), "Available Balance"),
	;
	private By locator;
	private String identifier;
	
	private ObjectRepo(By locator, String identifier) {
		this.locator = locator;
		this.identifier = identifier;
	}
	
	public By getLocator() {
		return locator;
	}
	
	public String getIdentifier() {
		return identifier;
	}
}
