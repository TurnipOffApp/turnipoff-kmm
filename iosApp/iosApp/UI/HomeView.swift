import SwiftUI
import shared

struct HomeView: View {

    var body: some View {
        GeometryReader { geometry in
            ScrollView(.vertical) {
                VStack {
                    TrendingView()
                        .frame(height: geometry.size.height * 0.5)
                    ForEach(SectionConfig.Category.allCases) { section in
                        Divider()
                        let config = SectionConfig.builder(category: section)
                        SectionView(viewModel: .init(config: config))
                            .frame(height: geometry.size.height * 0.35)
                    }
                    Spacer()
                }
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}

