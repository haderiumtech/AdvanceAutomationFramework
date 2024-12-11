package pages;

public class LoginXpaths {

	public final String dMurl = "https://directmacro.co.uk/";

	// home page title
	public final String homeTitle = "DirectMacro - UK";

	// search bar
	public final String searchBar = "//input[@placeholder = 'Enter Keywords to search...']";
	public final String searchData = "Corsair CL-9011133-WW Internal Power Cable Computer Cable";
	public final String searchBtn = "//button[@class='searchbtn']";
	public final String searchResultsPage = "//div[@class='ps-shopping__content']";
	public final String searchPageFirstRecord = "//div[@class='ps-product__content pb-0 ']/a[@class='ps-product__title']";

	// add to card (Quantity Check)
	public final String dMProductUrl = "https://directmacro.co.uk/category/data-storage";

	public final String PageFirstProduct = "//div[@class='ps-product__content pb-0 ']/a[@class='ps-product__title']";
	public final String AddtoCartBtn = "//div[@class='ps-product__shopping d-md-flex mt-0']/a[@class='ps-btn to-cart mr-3']";
	public final String Quantitycheck = "//span[@class='item-quantity']";
	public final String ProductCheckInCheckout = "//figure[@class='ps-block__items mb-5']";
	public final String Quantity = "1";

	// deleting items from cart
	public final String CartBtn = "//span[@class='d-block']";
	public final String ShoppingCartPageUrl = "https://directmacro.co.uk/account/shopping-cart";
	public final String CartItems = "//div[@class='ps-product__content']";
	public final String ItemCrossBtn = "//td/a/i[@class='icon-cross']";
	public final String RemovedItemToastMsg = "//div[@class='ant-notification-notice-message']";
	public final String CartEmptyAlert = "//div[@class='alert alert-info']/p[@class='mb-0']";
	public final String CartEmptyAlertExp = "Your cart is currently empty.";

	// request bulk quote form
	public final String ReqBulkQouteBtn = "//a[@class='ps-btn ps-secondary mt-3 mt-md-0']";
	public final String BulkQuoteForm = "//*[text()='Request Bulk Quote']";
	public final String BulkQouteName = "//input[@name='name']";
	public final String BulkQuoteOrganization = "//input[@name='organization']";
	public final String BulkQuoteEmail = "//input[@name='email']";
	public final String BulkQuotePhone = "//input[@name='phone']";
	public final String BulkQuoteQuantity = "//input[@name='quantity']";
	public final String BulkQuoteDeliveryDD = "//div[@class='ant-select-selector']";
	public final String BulkQuoteDeliveryDDToday = "//div[@title='Today']";
	public final String BulkQuoteDescription = "//textarea[@name='message']";
	public final String BulkQuoteSubmitBtn = "//button[@type='submit']";

	public final String BulkQouteNameValue = "Test Name";
	public final String BulkQuoteOrganizationValue = "Test Organization";
	public final String BulkQuoteEmailValue = "testdmuk@yopmail.com";
	public final String BulkQuotePhoneValue = "1234567890";
	public final String BulkQuoteQuantityValue = "500";
	public final String BulkQuoteDescriptionValue = "Test Description";

	public final String BulkQuoteThankYou = "//h2[@id='swal2-title']";
	public final String BulkQuoteThankYouExp = "Thank you!";

	// contact us page
	public final String dMContactUsUrl = "https://directmacro.co.uk/contact";

	public final String ContactName = "//input[@name='name']";
	public final String ContactEmail = "//input[@name='email']";
	public final String ContactPhone = "//input[@name='phone']";
	public final String ContactMessage = "//textarea[@name='message']";
	public final String ContactSubmitBtn = "//button[@class='ps-btn' and @type='submit']";

	public final String ContactNameValue = "Test Name";
	public final String ContactEmailValue = "testdmuk@yopmail.com";
	public final String ContactPhoneValue = "1234567890";
	public final String ContactMessageValue = "Test Message, Kindly ignore!";

	// check out page form
	public final String CheckOutFirstName = "//input[@id='firstName']";
	public final String CheckOutLastName = "//input[@id='lastName']";
	public final String CheckOutEmail = "//input[@id='email']";
	public final String CheckOutPhone = "//input[@id='phone']";

	public final String CheckOutAddress = "//input[@id='address']";
	public final String CheckOutState = "//input[@id='state']";
	public final String CheckOutStateValue = "//div[@title='Aberdeen']/div[text()='Aberdeen']";
	public final String CheckOutPostalCode = "//input[@id='zip']";

	public final String CheckOutCardIframe = "//iframe[@name[starts-with(., '__privateStripeFrame')]]";

	public final String CheckOutCardNumber = "//input[@name='cardnumber']";
	public final String CheckOutCardExp = "//input[@name='exp-date']";
	public final String CheckOutCardCVC = "//input[@name='cvc']";

	public final String CheckOutSubmitBtn = "//button[text()='Complete order']";

	public final String CardDeclineToastMessage = "//div[@class='ant-notification-notice-message' and text()='Card Declined']";

	public final String CheckOutFirstNameValue = "Test";
	public final String CheckOutLastNameValue = "Test";
	public final String CheckOutEmailValue = "test@yopmail.com";
	public final String CheckOutPhoneValue = "1234567890";

	public final String CheckOutAddressValue = "Test Address";
	public final String CheckOutPostalCodeValue = "12345";

	public final String CheckOutCardNumberValue = "4242424242424242";
	public final String CheckOutCardExpValue = "1234";
	public final String CheckOutCardCVCValue = "123";

}
