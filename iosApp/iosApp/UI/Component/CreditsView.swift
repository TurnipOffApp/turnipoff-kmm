//
//  CreditsView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CreditsView: View {

    // MARK: Properties

    let credits: MovieCredits

    // MARK: View

    var body: some View {
        VStack {
            CreditsListView(title: "Cast", credit: credits.cast)
            Divider()
            CreditsListView(title: "Crew", credit: credits.crew)
        }
        .padding()
    }

}

