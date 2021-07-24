package com.loyaltyworks.wavinseapp.utils.fetchData.apiCall

import com.loyaltyworks.wavinseapp.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// Live
//const val mobileApi: String = "MobileApp/MobileApi.svc/json/"
//const val se_mobileApi: String = "MobileApp/SeMobileApi.svc/JSON/"


// Demo
const val mobileApi: String = "WAVIN/Mobile/"
const val se_mobileApi: String = "WAVIN/SeMobile/"

interface ApiInterface {

    // Login Check api call
    @POST("${se_mobileApi}CheckIsAuthenticatedSEMobileApp")
    fun fetchLoginDataAsync(@Body loginRequest: LoginRequest): Deferred<Response<LoginResponse>>

    /* get Attribute Request*/
    @POST("${mobileApi}GetAttributeDetailsMobileApp")
    fun getAttributeDetailsAsync(@Body attributeRequest: AttributeRequest?): Deferred<Response<AttributeResponse>>

    /* User Mapped Parent*/
    @POST("${mobileApi}GetMappedParentChildEnrollAndTranDetails")
    fun getMappedParentDetailsAsync(@Body attributeRequest: UserMappedParentRequest?): Deferred<Response<UserMappedParentResponse>>

    /* Plumber Claim Status Request*/
    @POST("${se_mobileApi}GetPurchaseRequestDetailsForSE")
    fun getPlumberClaimStatusAsync(@Body attributeRequest: RetailerOrderRequest?): Deferred<Response<RetailerOrderResponse>>

    /* get order details Request*/
    @POST("${mobileApi}GetOrderDetails")
    fun getOrderDetailsAsync(@Body attributeRequest: OrderDetailsRequest?): Deferred<Response<OrderDetailsResponse>>

    /* Redemptions Status Request*/
    @POST("${mobileApi}GetCatalogueDetails")
    fun getRedemptionsStatusAsync(@Body attributeRequest: RedemptionsStatusRequest?): Deferred<Response<RedemptionsStatusResponse>>


    /* Dream Gift Request*/
    @POST("${mobileApi}GetDreamGiftDetails")
    fun getDreamGiftAsync(@Body dreamGiftRequest: DreamGiftRequest?): Deferred<Response<DreamGiftResponse>>

    /* Dream Gift Remove Request*/
    @POST("${se_mobileApi}SaveDreamGiftDetails")
    fun getDreamRemoveGiftAsync(@Body dreamGiftRequest: DreamGiftRemoveRequest?): Deferred<Response<DreamGiftRemoveResponse>>

    /* Dream Gift Add Request*/
    @POST("${se_mobileApi}SaveDreamGiftDetails")
    fun getDreamAddGiftAsync(@Body AddDreamGiftRequest: AddDreamGiftRequest?): Deferred<Response<AddDreamGiftResponse>>


    //Remove Dream Gift
    @POST("${mobileApi}SaveOrUpdateDreamGiftDetails")
    fun getRemoveDreamGiftMobileApp(@Body mydreamGiftRequest: RemoveDreamGiftRequest?): Deferred<Response<RemoveDreamGiftResponse>>

    /* get customer Details listing Request*/
    @POST("${se_mobileApi}GetCustomerDetailsForJson")
    fun getCustomerDetilsRequestAsync(@Body customerDetailsRequest: CustomerDetailsRequest?): Deferred<Response<CustomerDetailsResponse>>

    /* get tier listing Request*/
    @POST("${se_mobileApi}GetAttributeDetails")
    fun getTierListRequestAsync(@Body tierRequest: TierRequest?): Deferred<Response<TierResponse>>


    /* get member status Request*/
    @POST("${se_mobileApi}SaveCustomerDetails")
    fun getMemberStatusRequestAsync(@Body tierRequest: MemberStatusRequest?): Deferred<Response<MemberStatusResponse>>

    /*myRetailerListing Request*/
    @POST("${mobileApi}GetRedemptionPlannerDetails")
    fun getWishListRequestAsync(@Body wishListRequest: WishListRequest?): Deferred<Response<WishListResponse>>

    /*myRetailerListing Request*/
    @POST("${se_mobileApi}GetPlannerAddedOrNot")
    fun getRemoveWishListRequestAsync(@Body wishListRequest: RemoveWishListRequest?): Deferred<Response<RemoveWishListResponse>>

    /*myRetailerAddOr Not Request*/
    @POST("${mobileApi}GetCatalogueDetails")
    fun getAddOrNotWishListRequestAsync(@Body wishListRequest: AddOrNotWishListRequest?): Deferred<Response<AddOrNotWishListResponse>>

    /*myRetailerAddOr Not Request*/
    @POST("${mobileApi}GetPlannerAddedOrNotJson")
    fun getWishListAddedResponseAsync(@Body addedRequest: WishListAddedRequest?): Deferred<Response<WishListAddedResponse>>

    /*redeemGift*/
    @POST("${mobileApi}GetCatalogueCategoryDetails")
    fun setProductCategoryRequestAsync(@Body productCategoryRequest: ProductCategoryRequest): Deferred<Response<ProductCategoryResponse>>

    /*redeemGift*/
    @POST("${mobileApi}GetCatalogueDetails")
    fun getRedeemGiftAsync(@Body redeemGiftRequest: RedeemGiftRequest?): Deferred<Response<RedeemGiftResponse>>

    /*RedeemGiftCatalogueRequest*/
    @POST("${mobileApi}SaveCatalogueRedemptionDetails")
    fun getRedeemGiftCatalogueRequestAsync(@Body redeemGiftCatalogueRequest: RedeemGiftCatalogueRequest?): Deferred<Response<RedeemGiftCatalogueResponse>>

    /*RedeemGiftVoucher*/
    @POST("${mobileApi}SaveCatalogueRedemptionDetailsEssilor")
    fun getRedeemGiftVoucherAsync(@Body redeemGiftVoucherRequest: RedeemGiftVoucherRequest?): Deferred<Response<RedeemGiftVoucherResponse>>

    /*RedeemGiftCatalogue Send ALert Mobile App Request*/
    @POST("${mobileApi}SendCatalogueRedemptionAlertMobileApp")
    fun getSendCatalogueRedemptionAlertMobileAppRequestAsync(@Body sendCatalogueRedemptionAlertMobileAppRequest: SendCatalogueRedemptionAlertMobileAppRequest?): Deferred<Response<SendCatalogueRedemptionAlertMobileAppResponse>>

    /*RedeemGiftCatalogue Send ALert Mobile App Request*/
    @POST("${mobileApi}SendSMSForSuccessfulRedemptionMobileApp")
    fun getSendSMSSuccessRedemptionRequestAsync(@Body sendSMSForSuccessfulRedemptionMobileAppRequest: SendSMSForSuccessfulRedemptionMobileAppRequest?): Deferred<Response<SendSMSForSuccessfulRedemptionMobileAppResponse>>

    // OTP api call
    @POST("${mobileApi}SaveAndGetOTPDetails")
    fun getOTPAsync(@Body otpRequest: OTPRequest): Deferred<Response<OTPResponse>>

    /*myProfileRequest*/
    @POST("${mobileApi}GetCustomerDetailsMobileApp")
    fun getMyProfileRequestAsync(@Body myProfileRequest: MyProfileRequest?): Deferred<Response<MyProfileResponse>>

    /*State Request*/
    @POST("${mobileApi}GetStateDetailsMobileApp")
    fun getStateRequestAsync(@Body getStateRequest: GetStateRequest?): Deferred<Response<GetStateResponse>>

    /*City Request*/
    @POST("${mobileApi}GetCityDetails")
    fun getCityRequestAsync(@Body getCityDetailsRequest: GetCityDetailsRequest?): Deferred<Response<GetCityDetailsResponse>>

    /*District Request*/
    @POST("${mobileApi}GetDistrictDetails")
    fun getDistrictRequestAsync(@Body districtRequest: DistrictRequest?): Deferred<Response<DistrictResponse>>

    // DashBoard api call
    @POST("${mobileApi}GetMerchantEmailDetailsMobileApp")
    fun fetchDashBoardAsync(@Body dashboardRequest: DashboardRequest): Deferred<Response<DashboardResponse>>

    // get Dashboard data 2 api call
    @POST("${mobileApi}getCustomerDashboardDetailsMobileApp")
    fun fetchDashBoardDetailsAsync(@Body dashboardCustomerRequest: DashboardCustomerRequest): Deferred<Response<DashboardCustomerResponse>>

    @POST("${mobileApi}GetPushHistoryDetails")
    fun getHistoryNotifiation(@Body historyNotificationRequest: HistoryNotificationRequest?): Deferred<Response<HistoryNotificationResponse>>

    @POST("${mobileApi}GetPushHistoryDetails")
    fun getHistoryNotifiationDetailByID(@Body historyNotificationDetailByIDRequest: HistoryNotificationDetailsRequest?): Deferred<Response<HistoryNotificationResponse>>

    /*History Notification Delete by Id*/
    @POST("${mobileApi}GetPushHistoryDetails")
    fun getHistoryNotifiationDeleteByID(@Body historyNotificationDetailByIDRequest: HistoryNotificationDetailsRequest?): Deferred<Response<HistoryNotificationDeleteResponse>>


    @POST("${mobileApi}GetCustomerDetailsMobileApp")
    fun getSelectedRetailerDetailByCustomerIDRequestAsync(@Body getSelectedRetailerDetailByCustomerIDRequest: GetSelectedRetailerDetailByCustomerIDRequest?): Deferred<Response<GetSelectedRetailerDetailByIDResponse>>

    @POST("${mobileApi}GetAttributeDetailsMobileApp")
    fun getCustomerCategoryRequestAsync(@Body attributeRequest: AttributeRequest?): Deferred<Response<AttributeResponse>>

    @POST("${mobileApi}GetAttributeDetailsMobileApp")
    fun getDistributorRequestAsync(@Body attributeRequest: AttributeRequest?): Deferred<Response<AttributeResponse>>

    /* get Language listing Request*/
    @POST("${se_mobileApi}GetAttributeDetails")
    fun getLanguageRequestAsync(@Body tierRequest: TierRequest?): Deferred<Response<TierResponse>>

    /* get CustomerType listing Request*/
    @POST("${se_mobileApi}GetAttributeDetails")
    fun getCustomerTypeRequestAsync(@Body tierRequest: TierRequest?): Deferred<Response<TierResponse>>

    // get CheckCustomerExistancy api call
    @POST("${mobileApi}CheckUserNameExists")
    fun getCustomerMobileExistancyAsync(@Body checkCustomerExistancyAndVerificationRequest: CheckCustomerExistancyAndVerificationRequest): Deferred<Response<EmailCheckResponse>>

    // get OTP api call
    @POST("${mobileApi}SaveAndGetOTPDetails")
    fun getSaveAndGetOTPDetails(@Body saveAndGetOTPDetailsRequest: SaveAndGetOTPDetailsRequest): Deferred<Response<SaveAndGetOTPDetailsResponse>>

  // get generalRequest api call
    @POST("${se_mobileApi}SaveCustomerDetails")
    fun getGeneralRequest(@Body generalRequest: GeneralRequest): Deferred<Response<GeneralResponse>>

 // get generalRequest api call
    @POST("${mobileApi}GetCountryDetails")
    fun getCountryDetails(@Body countryRequest: CountryRequest): Deferred<Response<CountryResponse>>

// get districtRequest api call
    @POST("${mobileApi}GetDistrictDetails")
    fun getDistrictDetails(@Body districtRequest: DistrictRequest): Deferred<Response<DistrictResponse>>

    // get stateRequest api call
    @POST("${mobileApi}GetStateDetails")
    fun getStateDetails(@Body stateRequest: StateRequest): Deferred<Response<StateResponse>>

    // get native-stateRequest api call
    @POST("${mobileApi}GetStateDetails")
    fun getNativeStateDetails(@Body stateRequest: StateRequest): Deferred<Response<StateResponse>>

  // get getPersonalSaveUpdateRequestDetails api call
    @POST("${se_mobileApi}SaveCustomerDetails")
    fun getPersonalSaveUpdateRequestDetails(@Body personalSaveUpdateRequest: PersonalSaveUpdateRequest): Deferred<Response<GeneralResponse>>


    /*MyEarning Histrory Lisitng */
    @POST("${mobileApi}GetRewardTransactionDetailsMobileApp")
    fun getMyEarningListing(@Body myEarningRequest: MyEarningRequest?):  Deferred<Response<MyEarningResponse>>

    /*MyRedemption Histrory Lisitng */
    @POST("${mobileApi}GetCustomerRedemptionDetailsMobileApp")
    fun getMyRedemptionListing(@Body getRedemptionRequest: MyRedemptionRequest?):  Deferred<Response<MyRedemptionResponse>>

    /*Promotion Detail By Id*/
    @POST("${mobileApi}GetCustomerPromotionDetailsByPromotionID")
    fun getCustomerPromotionDetailsByPromotionID(@Body getPromotionDetailsRequest: GetPromotionDetailsRequest?):  Deferred<Response<GetPromotionResponse>>

    /*Promotion Listing */
    @POST("${mobileApi}GetPromotionDetailsMobileApp")
    fun getPromotionDetailsMobileApp(@Body getPromotionRequest: GetWhatsNewRequest?):  Deferred<Response<GetPromotionResponse>>

    // Email Domain check api call
    @POST("${mobileApi}SaveCustomerPromotionDetails")
    fun setCustomerPromotionRequestAsync(@Body saveCustomerPromotionRequest: SaveCustomerPromotionRequest): Deferred<Response<SaveCustomerPromotionResponse>>

  // identificationNumberExistencyCheckRequest check api call
    @POST("${mobileApi}CheckUserNameExists")
    fun setIdentificationNumberExistencyCheckRequestAsync(@Body identificationNumberExistencyCheckRequest: IdentificationNumberExistencyCheckRequest): Deferred<Response<IdentificationNumberExistencyCheckResponse>>

 // identificationNumberExistencyCheckRequest check api call
    @POST("${se_mobileApi}SaveCustomerDetails")
    fun setSaveUpdateIdentificationDetailRequestAsync(@Body identificationSaveUpdateRequest: IdentificationSaveUpdateRequest): Deferred<Response<SaveUpdateIdentificationDetailResponse>>

    // saveUpdateBusinessRequest check api call
    @POST("${se_mobileApi}SaveCustomerDetails")
    fun setSaveUpdateBusinessRequestAsync(@Body saveUpdateBusinessRequest: SaveUpdateBusinessRequest): Deferred<Response<SaveUpdateBusinessResponse>>

    //Query listing api call
    @POST("${mobileApi}SaveCustomerQueryTicket")
    fun qetQueryListingQueryResponse(@Body queryListingRequest: QueryListingRequest): Deferred<Response<QueryListingResponse>>

    //Help Topic listing api call
    @POST("${mobileApi}GetHelpTopics")
    fun getHelpTopicHeadersResponse(@Body helpTopicRetrieveRequest: HelpTopicRetrieveRequest): Deferred<Response<GetHelpTopicResponse>>

    @POST("${mobileApi}SaveCustomerQueryTicket")
    fun getSaveNewTicketQueryResponse(@Body saveNewTicketQueryRequest: SaveNewTicketQueryRequest?): Deferred<Response<SaveNewTicketQueryResponse>>

    //QueryChat api call
    @POST("${mobileApi}GetQueryResponseInformation")
    fun getQueryChatElementResponse(@Body queryChatElementRequest: QueryChatElementRequest?): Deferred<Response<QueryChatElementResponse>>

    //PostReplyQueryChatStatus api call
    @POST("${mobileApi}SaveCustomerQueryTicket")
    fun getPostReplyHelpTopicStatus(@Body queryChatElementRequest: PostChatStatusRequest?): Deferred<Response<PostChatStatusResponse>>

      //seQueryListingRequest api call
    @POST("${se_mobileApi}GetCustomerQueriesDetailsJSON")
    fun getSEQueryElementResponse(@Body seQueryListingRequest: SEQueryListingRequest?): Deferred<Response<SEQueryListingResponse>>

    //seQueryChatRequest api call
    @POST("${se_mobileApi}GetQueryResponseInformation")
    fun getSEQueryChatResponse(@Body seQueryChatRequest: SEQueryChatRequest?): Deferred<Response<SEQueryChatResponse>>

    //seQueryChatReplyRequest api call
    @POST("${se_mobileApi}ReplyToCustomerQuery")
    fun getSEQueryChatReplyResponse(@Body seQueryChatReplyRequest: SEQueryChatReplyRequest?): Deferred<Response<SEQueryChatReplyResponse>>

    //Forgot Password
    @POST("${mobileApi}forgotPasswordMobileApp")
    fun getForgotPasswordResponse(@Body forgotPasswordRequest: ForgotPasswordRequest?): Deferred<Response<ForgotPasswordResponse>>

}