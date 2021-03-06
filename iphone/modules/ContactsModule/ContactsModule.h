/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import <Foundation/Foundation.h>
#import "TitaniumModule.h"
#import <AddressBook/AddressBook.h>


@interface ContactsModule : NSObject<TitaniumModule> {
	int nextContactPickerToken;
	NSMutableDictionary * contactPickerLookup;
	ABAddressBookRef sharedAddressBook;
}

@end
