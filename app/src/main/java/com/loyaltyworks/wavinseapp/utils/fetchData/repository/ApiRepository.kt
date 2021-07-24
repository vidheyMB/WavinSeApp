package com.loyaltyworks.wavinseapp.utils.fetchData.repository

import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.utils.fetchData.apiCall.ApiInterface


class ApiRepository(private val apiInterface: ApiInterface) : BaseRepository() {

    suspend fun getHistoryNotificationList(historyNotificationRequest: HistoryNotificationRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiation(historyNotificationRequest).await()
            },
            error = "Error History Notification Trigger"
        )
    }

    suspend fun getHistoryNotificationDetailByIdList(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest): HistoryNotificationResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiationDetailByID(historyNotificationDetailsRequest)
                    .await()
            },
            error = "Error History Notification Detail By ID Trigger"
        )
    }

    suspend fun getHistoryNotificationDeleteByIdList(historyNotificationDetailsRequest: HistoryNotificationDetailsRequest): HistoryNotificationDeleteResponse? {
        return safeApiCall(
            call = {
                apiInterface.getHistoryNotifiationDeleteByID(historyNotificationDetailsRequest)
                    .await()
            },
            error = "Error History Notification Detail By ID Trigger"
        )
    }


    // DashBoard 2 api call
    suspend fun getDashBoardData2(dashboardCustomerRequest: DashboardCustomerRequest): DashboardCustomerResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashBoardDetailsAsync(dashboardCustomerRequest).await() },
            error = "Error fetching Dashboard Details 2"
            //convert to mutable list
        )
    }


    // Login Check api call
    suspend fun getLoginData(loginRequest: LoginRequest): LoginResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchLoginDataAsync(loginRequest).await() },
            error = "Error fetching Login Details"
            //convert to mutable list
        )
    }


    suspend fun getCustomerDetailsData(customerDetailsRequest: CustomerDetailsRequest): CustomerDetailsResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerDetilsRequestAsync(customerDetailsRequest).await()
            },
            error = "Error CustomerDetails Trigger"
        )
    }

    suspend fun getAttributeDetailsRequestData(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            call = {
                apiInterface.getAttributeDetailsAsync(attributeRequest).await()
            },
            error = "Error Retailer Order Request" +
                    " Trigger"
        )
    }

    suspend fun getUserMappedRequestData(usermappedParentRequest: UserMappedParentRequest): UserMappedParentResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMappedParentDetailsAsync(usermappedParentRequest).await()
            },
            error = "Error Mapped Parent Request" +
                    " Trigger"
        )
    }

    suspend fun getPlumberClaimRequestData(plumberClaimStatusRequest: RetailerOrderRequest): RetailerOrderResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPlumberClaimStatusAsync(plumberClaimStatusRequest).await()
            },
            error = "Error Plumber Claim Status Request" +
                    " Trigger"
        )
    }


    suspend fun getOrderDetailsData(plumberClaimStatusRequest: OrderDetailsRequest): OrderDetailsResponse? {
        return safeApiCall(
            call = {
                apiInterface.getOrderDetailsAsync(plumberClaimStatusRequest).await()
            },
            error = "Error Order Detials Request" +
                    " Trigger"
        )
    }


    suspend fun getRedemptionsStatusData(redemptionsStatusRequest: RedemptionsStatusRequest): RedemptionsStatusResponse? {
        return safeApiCall(
            call = {
                apiInterface.getRedemptionsStatusAsync(redemptionsStatusRequest).await()
            },
            error = "Error Redemptions Status Request" +
                    " Trigger"
        )
    }



    suspend fun getDreamGiftData(dreamGiftRequest: DreamGiftRequest): DreamGiftResponse? {
        return safeApiCall(
            call = {
                apiInterface.getDreamGiftAsync(dreamGiftRequest).await()
            },
            error = "Error Dream Gift Request" +
                    " Trigger"
        )
    }

    suspend fun getDreamGiftRemoveData(dreamGiftRemoveRequest: DreamGiftRemoveRequest): DreamGiftRemoveResponse? {
        return safeApiCall(
            call = {
                apiInterface.getDreamRemoveGiftAsync(dreamGiftRemoveRequest).await()
            },
            error = "Error Dream Gift Request" +
                    " Trigger"
        )
    }

    suspend fun getDreamGiftAddData(AddDreamGiftRequest: AddDreamGiftRequest): AddDreamGiftResponse? {
        return safeApiCall(
            call = {
                apiInterface.getDreamAddGiftAsync(AddDreamGiftRequest).await()
            },
            error = "Error Add Dream Gift Request" +
                    " Trigger"
        )
    }

    /*Remove DreamGfit Api Call*/
    suspend fun getRemoveDreamGift(whatsNewRequest: RemoveDreamGiftRequest): RemoveDreamGiftResponse? {
        return safeApiCall(
            call = {
                apiInterface.getRemoveDreamGiftMobileApp(whatsNewRequest).await()
            },
            error = "Error  DreamGift Trigger"
        )
    }

    suspend fun getTierListingData(tierRequest: TierRequest): TierResponse? {
        return safeApiCall(
            call = {
                apiInterface.getTierListRequestAsync(tierRequest).await()
            },
            error = "Error TierListing Trigger"
        )
    }

    suspend fun getWishListData(wishListRequest: WishListRequest): WishListResponse? {
        return safeApiCall(
            call = {
                apiInterface.getWishListRequestAsync(wishListRequest).await()
            },
            error = "Error WishListRequest Trigger"
        )
    }

    suspend fun getRemoveWishListData(wishListRequest: RemoveWishListRequest): RemoveWishListResponse? {
        return safeApiCall(
            call = {
                apiInterface.getRemoveWishListRequestAsync(wishListRequest).await()
            },
            error = "Error RemoveWishListRequest Trigger"
        )
    }


    suspend fun getAddOrNotWishListData(addOrNotWishListRequest: AddOrNotWishListRequest): AddOrNotWishListResponse? {
        return safeApiCall(
            call = {
                apiInterface.getAddOrNotWishListRequestAsync(addOrNotWishListRequest).await()
            },
            error = "Error AddOrNotWishListRequest Trigger"
        )
    }

    suspend fun getWishListAddedRequest(wishListAddedRequest: WishListAddedRequest): WishListAddedResponse? {
        return safeApiCall(
            call = {
                apiInterface.getWishListAddedResponseAsync(wishListAddedRequest).await()
            },
            error = "Error wishListAddedRequest Trigger"
        )
    }



    suspend fun getProductCategory(productCategoryRequest: ProductCategoryRequest): ProductCategoryResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.setProductCategoryRequestAsync(productCategoryRequest).await() },
            error = "Error productCategoryRequest"
            //convert to mutable list
        )
    }

    /*RedeemGift Catalogue and Voucher Call back*/
    suspend fun getRedeemGiftData(redeemGiftRequest: RedeemGiftRequest): RedeemGiftResponse? {
        return safeApiCall(
            call = {
                apiInterface.getRedeemGiftAsync(redeemGiftRequest).await()
            },
            error = "Error RedeemGiftRequest Trigger"
        )
    }

    /*Redeem Gift Catalogue Request Call back*/
    suspend fun getRedeemGiftCatalogueRequest(redeemGiftCatalogueRequest: RedeemGiftCatalogueRequest): RedeemGiftCatalogueResponse? {

        return safeApiCall(
            call = {
                apiInterface.getRedeemGiftCatalogueRequestAsync(redeemGiftCatalogueRequest).await()
            },
            error = "Error RedeemGiftCatalogueRequestAsync Trigger"
        )

    }

    /*RedeemGiftVoucher Request*/
    suspend fun getRedeemGiftVoucherRequest(redeemGiftVoucherRequest: RedeemGiftVoucherRequest): RedeemGiftVoucherResponse? {
        return safeApiCall(
            call = {
                apiInterface.getRedeemGiftVoucherAsync(redeemGiftVoucherRequest).await()
            },
            error = "Error RedeemGiftRequest Trigger"
        )
    }

    /*Redeem Gift Catalogue Request Call back*/
    suspend fun getSendCatalogueRedemptionAlertMobileAppRequest(sendCatalogueRedemptionAlertMobileAppRequest: SendCatalogueRedemptionAlertMobileAppRequest): SendCatalogueRedemptionAlertMobileAppResponse? {

        return safeApiCall(
            call = {
                apiInterface.getSendCatalogueRedemptionAlertMobileAppRequestAsync(sendCatalogueRedemptionAlertMobileAppRequest).await()
            },
            error = "Error SendCatalogueRedemptionAlertMobileAppRequestAsync Trigger"
        )

    }

    /*Send SUCCESS SMS Redemption Request Call back */
    suspend fun getSendSMSSuccessRedemptionRequest(sendSMSForSuccessfulRedemptionMobileAppRequest: SendSMSForSuccessfulRedemptionMobileAppRequest): SendSMSForSuccessfulRedemptionMobileAppResponse? {
        return safeApiCall(
            call = {
                apiInterface.getSendSMSSuccessRedemptionRequestAsync(sendSMSForSuccessfulRedemptionMobileAppRequest).await()
            },
            error = "Error SendSMSSuccessRedemptionRequest Trigger"
        )
    }


    // OTP api call
    suspend fun getOTP(otpRequest: OTPRequest): OTPResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getOTPAsync(otpRequest).await() },
            error = "Error OTP trigger"
            //convert to mutable list
        )
    }

    /*My Profile Request Call back*/
    suspend fun getProfileRequestData(myProfileRequest: MyProfileRequest): MyProfileResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMyProfileRequestAsync(myProfileRequest).await()
            },
            error = "Error RedeemGiftRequest Trigger"
        )
    }

    suspend fun getStateData(stateRequest: GetStateRequest): GetStateResponse? {
        return safeApiCall(
            call = {
                apiInterface.getStateRequestAsync(stateRequest).await()
            },
            error = "Error stateRequest Trigger"
        )
    }

    suspend fun getCityData(cityDetailsRequest: GetCityDetailsRequest): GetCityDetailsResponse? {

        return safeApiCall(
            call = {
                apiInterface.getCityRequestAsync(cityDetailsRequest).await()
            },
            error = "Error CityRequest Trigger"
        )
    }

    suspend fun getDistrictData(districtRequest: DistrictRequest): DistrictResponse? {

        return safeApiCall(
            call = {
                apiInterface.getDistrictRequestAsync(districtRequest).await()
            },
            error = "Error DistrictRequest Trigger"
        )
    }

    /*Hemant API request callback*/

    /*selectedRetailerDetailByCustomerIDRequest*/

    // DashBoard api call
    suspend fun getDashBoardData(dashboardRequest: DashboardRequest): DashboardResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.fetchDashBoardAsync(dashboardRequest).await() },
            error = "Error fetching Dashboard Details"
            //convert to mutable list
        )
    }


    suspend fun getSelectedRetailerDetailsByCustomerIDData(selectedRetailerDetailByCustomerIDRequest: GetSelectedRetailerDetailByCustomerIDRequest): GetSelectedRetailerDetailByIDResponse? {
        return safeApiCall(
            call = {
                apiInterface.getSelectedRetailerDetailByCustomerIDRequestAsync(selectedRetailerDetailByCustomerIDRequest).await()
            },
            error = "Error selectedRetailerDetailByCustomerIDRequest Trigger"
        )
    }

    /*CustomerCategory API callback*/
    suspend fun getCustomerCategoryData(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerCategoryRequestAsync(attributeRequest).await()
            },
            error = "Error attributeRequest Trigger"
        )
    }

    /*Distributor API callback*/
    suspend fun getDistributorData(attributeRequest: AttributeRequest): AttributeResponse? {
        return safeApiCall(
            call = {
                apiInterface.getDistributorRequestAsync(attributeRequest).await()
            },
            error = "Error Distributor Trigger"
        )
    }

    suspend fun getLanguageTypeListingData(tierRequest: TierRequest): TierResponse? {
        return safeApiCall(
            call = {
                apiInterface.getLanguageRequestAsync(tierRequest).await()
            },
            error = "Error Language Trigger"
        )
    }

    suspend fun getCustomerTypeListingData(tierRequest: TierRequest): TierResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerTypeRequestAsync(tierRequest).await()
            },
            error = "Error CustomerType Trigger"
        )
    }


    // email api call
    suspend fun getEmailExist(emailCheckRequest: CheckCustomerExistancyAndVerificationRequest): EmailCheckResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCustomerMobileExistancyAsync(emailCheckRequest).await() },
            error = "Error email check"
            //convert to mutable list
        )
    }


    /*OTP Call back for Activate Retail_Distributor*/
    suspend fun getOTPDetail(saveAndGetOTPDetailsRequest: SaveAndGetOTPDetailsRequest): SaveAndGetOTPDetailsResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getSaveAndGetOTPDetails(saveAndGetOTPDetailsRequest).await() },
            error = "Error saveAndGetOTPDetailsRequest check"
            //convert to mutable list
        )
    }

    suspend fun getGeneralSubmitData(generalRequest: GeneralRequest): GeneralResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getGeneralRequest(generalRequest).await() },
            error = "Error generalRequest check"
            //convert to mutable list
        )
    }

    suspend fun getCountryListingData(countryRequest: CountryRequest): CountryResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getCountryDetails(countryRequest).await() },
            error = "Error countryRequest check"
            //convert to mutable list
        )
    }

    suspend fun getDistrictListingData(districtRequest: DistrictRequest): DistrictResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getDistrictDetails(districtRequest).await() },
            error = "Error districtRequest check"
            //convert to mutable list
        )
    }

    suspend fun getNativeStateListingData(stateRequest: StateRequest): StateResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getNativeStateDetails(stateRequest).await() },
            error = "Error native-stateRequest check"
            //convert to mutable list
        )
    }

    suspend fun getStateListingData(stateRequest: StateRequest): StateResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getStateDetails(stateRequest).await() },
            error = "Error stateRequest check"
            //convert to mutable list
        )
    }

    suspend fun getPersonalSaveUpdateData(personalSaveUpdateRequest: PersonalSaveUpdateRequest): GeneralResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = { apiInterface.getPersonalSaveUpdateRequestDetails(personalSaveUpdateRequest).await() },
            error = "Error getPersonalSaveUpdateRequestDetails check"
            //convert to mutable list
        )
    }

    /*My Earning History Listing APi Call*/
    suspend fun getMyEarningDetailRequest(myEarningRequest: MyEarningRequest): MyEarningResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMyEarningListing(myEarningRequest).await()
            },
            error = "Error MyEarningListing Trigger"
        )
    }

    /*My Redemption Request callback*/
    suspend fun getRedemptionDetailRequest(myRedemptionRequest: MyRedemptionRequest): MyRedemptionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMyRedemptionListing(myRedemptionRequest).await()
            },
            error = "Error MyRedemptionListing Trigger"
        )
    }


    /*Promotin Lising*/
    suspend fun getPromotionList(whatsNewRequest: GetWhatsNewRequest): GetPromotionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPromotionDetailsMobileApp(whatsNewRequest).await()
            },
            error = "Error  Promotion Trigger"
        )
    }


    // get promotin by id
    suspend fun getPromotionDetail(promotionDetailsRequest: GetPromotionDetailsRequest): GetPromotionResponse? {
        return safeApiCall(
            call = {
                apiInterface.getCustomerPromotionDetailsByPromotionID(promotionDetailsRequest)
                    .await()
            },
            error = "Error  Promotion Detail Trigger"
        )
    }


    // save promotion details
    suspend fun setCustomerPromotionRequest(saveCustomerPromotionRequest: SaveCustomerPromotionRequest): SaveCustomerPromotionResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = {
                apiInterface.setCustomerPromotionRequestAsync(saveCustomerPromotionRequest).await()
            },
            error = "Error email domain check"
            //convert to mutable list
        )
    }

    /*setIdentificationNumberExistencyCheckRequestAsync*/
    suspend fun getIdentificationNumberExistencyCheckData(identificationNumberExistencyCheckRequest: IdentificationNumberExistencyCheckRequest): IdentificationNumberExistencyCheckResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = {
                apiInterface.setIdentificationNumberExistencyCheckRequestAsync(identificationNumberExistencyCheckRequest).await()
            },
            error = "Error identificationNumberExistencyCheckRequest check"
            //convert to mutable list
        )
    }

    suspend fun getSaveUpdateIdentificationDetail(identificationSaveUpdateRequest: IdentificationSaveUpdateRequest): SaveUpdateIdentificationDetailResponse? {
        return safeApiCall(
            //await the result of deferred type
            call = {
                apiInterface.setSaveUpdateIdentificationDetailRequestAsync(identificationSaveUpdateRequest).await()
            },
            error = "Error setSaveUpdateIdentificationDetailRequestAsync check"
            //convert to mutable list
        )
    }


    suspend fun getFirmTypeListingData(tierRequest: TierRequest): TierResponse? {
        return safeApiCall(
            call = {
                apiInterface.getTierListRequestAsync(tierRequest).await()
            },
            error = "Error FirmTypeListing Trigger"
        )
    }

    suspend fun getMemberStatusData(memberStatusRequest: MemberStatusRequest): MemberStatusResponse? {
        return safeApiCall(
            call = {
                apiInterface.getMemberStatusRequestAsync(memberStatusRequest).await()
            },
            error = "Error MemberStatus Trigger"
        )
    }


    suspend fun getJobTypeListingData(tierRequest: TierRequest): TierResponse? {
        return safeApiCall(
            call = {
                apiInterface.getTierListRequestAsync(tierRequest).await()
            },
            error = "Error JobTypeListing Trigger"
        )
    }

    suspend fun getSaveUpdateBusinessRequestData(saveUpdateBusinessRequest: SaveUpdateBusinessRequest): SaveUpdateBusinessResponse? {
        return safeApiCall(
            call = {
                apiInterface.setSaveUpdateBusinessRequestAsync(saveUpdateBusinessRequest).await()
            },
            error = "Error saveUpdateBusinessRequest Trigger"
        )
    }

    suspend fun getSaveNewTicketQuery(saveNewTicketQueryRequest: SaveNewTicketQueryRequest): SaveNewTicketQueryResponse? {
        return safeApiCall(
            call = {
                apiInterface.getSaveNewTicketQueryResponse(saveNewTicketQueryRequest).await()
            },
            error = "Error  Promotion Detail Trigger"
        )
    }

    suspend fun getPostReply(postChatStatusRequest: PostChatStatusRequest): PostChatStatusResponse? {
        return safeApiCall(
            call = {
                apiInterface.getPostReplyHelpTopicStatus(postChatStatusRequest).await()
            },
            error = "Error PostReplyHelpTopicStatus Trigger"
        )
    }

    //QueryList for support api call
    suspend fun getQueryListData(queryListingRequest: QueryListingRequest): QueryListingResponse? {

        return safeApiCall(
            call = { apiInterface.qetQueryListingQueryResponse(queryListingRequest).await() },
            error = "Error Query List trigger"
        )

    }

    /*Help topic listing*/
    suspend fun getHelpTopic(topicListRequest: HelpTopicRetrieveRequest): GetHelpTopicResponse? {
        return safeApiCall(
            call = { apiInterface.getHelpTopicHeadersResponse(topicListRequest).await() },
            error = "Error help topic list trigger"
        )
    }

    /*QueryChat for specific queryID*/
    suspend fun getChatQuery(chatQuery: QueryChatElementRequest): QueryChatElementResponse? {
        return safeApiCall(
            call = { apiInterface.getQueryChatElementResponse(chatQuery).await() },
            error = "Error Query Chat trigger"
        )
    }

    suspend fun getSEQueryData(seQueryListingRequest: SEQueryListingRequest): SEQueryListingResponse? {
        return safeApiCall(
            call = { apiInterface.getSEQueryElementResponse(seQueryListingRequest).await() },
            error = "Error seQueryListingRequest trigger"
        )
    }

    suspend fun getQueryChatRequest(seQueryChatRequest: SEQueryChatRequest): SEQueryChatResponse? {
        return safeApiCall(
            call = { apiInterface.getSEQueryChatResponse(seQueryChatRequest).await() },
            error = "Error seQueryChatRequest trigger"
        )
    }

    suspend fun getQueryChatReplyRequest(seQueryChatReplyRequest: SEQueryChatReplyRequest): SEQueryChatReplyResponse? {
        return safeApiCall(
            call = { apiInterface.getSEQueryChatReplyResponse(seQueryChatReplyRequest).await() },
            error = "Error seQueryChatReplyRequest trigger"
        )
    }

    suspend fun getForgotPasswordData(forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse? {
        return safeApiCall(
            call = {
                apiInterface.getForgotPasswordResponse(forgotPasswordRequest).await()
            },
            error = "Error Forgot Password Trigger"
        )
    }

    /*Hemant API request callback*/

}
