//
//  MovieDescriptionView.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 27/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MovieDescriptionView: View {

    // MARK: Properties

    static var dateFormatter: DateFormatter = {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy"
        return dateFormatter
    }()
    static var listFormatter: ListFormatter = {
        let listFormatter = ListFormatter()
        return listFormatter
    }()
    static var dateComponentsFormatter: DateComponentsFormatter = {
        let dateComponentsFormatter = DateComponentsFormatter()
        dateComponentsFormatter.unitsStyle = .brief
        dateComponentsFormatter.allowedUnits = [.hour, .minute]
        dateComponentsFormatter.zeroFormattingBehavior = [.pad]
        return dateComponentsFormatter
    }()

    let movie: Movie

    // MARK: View

    var body: some View {
        HStack {
            Text(String(movie.voteAverage))
                .padding(15)
                .background(
                    Circle()
                        .stroke(.gray, lineWidth: 2)
                        .padding(6)
                )
                .font(.title2)

            Spacer()

            VStack(alignment: .trailing) {
                Text(movie.title)
                    .font(.title2)
                Group {
                    Text(movie.genresString)
                    Text(movie.releaseYear)
                }
                .font(.subheadline)

                Text(movie.duration)
            }
            .multilineTextAlignment(.trailing)
            .lineLimit(1)
            .scaledToFit()
            .minimumScaleFactor(0.5)

        }
        .padding([.leading, .trailing])
    }

}
