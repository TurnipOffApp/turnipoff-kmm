//
//  PersonOverviewView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 28/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PersonOverviewView: View {

    // MARK: Properties

    let person: Person

    // MARK: View

    var body: some View {
        Group {
            if let biography = person.biography, !biography.isEmpty {
                Text(biography)
            } else {
                Text("No biography.")
            }
        }
        .padding()
    }

}
