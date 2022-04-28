//
//  PersonDescriptionView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 28/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PersonDescriptionView: View {

    // MARK: Properties

    static var dateFormatter: DateFormatter = {
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .long
        dateFormatter.timeStyle = .none
        return dateFormatter
    }()

    let person: Person
    let credits: MovieCredits

    // MARK: View

    var body: some View {
        HStack {
            Text(String(format: "%.1f", average))
                .padding(15)
                .background(
                    Circle()
                        .stroke(.gray, lineWidth: 2)
                        .padding(6)
                )
                .font(.title)

            Spacer()

            VStack(alignment: .trailing) {
                Text(person.name)
                    .font(.title2)
                Group {
                    if let birthDate = person.birthDate {
                        Text(birthDate)
                    }
                    if let bornLocation = person.placeOfBirth {
                        Text(bornLocation)
                    }
                }
                .font(.subheadline)
            }
            .multilineTextAlignment(.trailing)
            .lineLimit(1)
            .scaledToFit()
            .minimumScaleFactor(0.5)

        }
        .padding([.leading, .trailing])
    }

    private var average: Double { credits.cast.map(\.average).average }

}
