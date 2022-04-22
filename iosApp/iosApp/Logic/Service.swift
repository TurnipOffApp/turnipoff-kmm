//
//  Service.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 22/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

public class Service {
    let client: TheMovieDBClient
    
    public static let shared = Service()

    private init() {
        client = TheMovieDBClient()
    }
}
