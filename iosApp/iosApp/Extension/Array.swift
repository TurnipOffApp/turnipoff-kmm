//
//  Array.swift
//  iosApp
//
//  Created by Jessy Bonnotte on 28/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension Array where Element: BinaryFloatingPoint {

    /// The average value of all the items in the array
    var average: Double {
        if self.isEmpty {
            return 0.0
        } else {
            let sum = self.reduce(0, +)
            return Double(sum) / Double(self.count)
        }
    }

}
