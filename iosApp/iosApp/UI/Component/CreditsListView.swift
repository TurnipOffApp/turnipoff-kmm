//
//  CreditsListView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CreditsListView: View {

    // MARK: Properties

    let title: String
    let credit: [MovieCredits.Credit]

    // MARK: View

    var body: some View {
        VStack {
            Text(title)
                .font(.headline)
            ScrollView(.horizontal) {
                LazyHStack {
                    ForEach(credit, id: \.internalID) {
                        CreditView(credit: $0)
                    }
                }
            }
        }
    }
}
