import facade from './util/apiFacade'

const Reviews = () => {
    const reviews = [
        { id: 1, text: "Review 1..." },
        { id: 2, text: "Review 2..." },
        { id: 3, text: "Review 3..." },
        { id: 4, text: "Review 4..." },
    ];

    return (
        <div>
            {reviews.map((review) => (
                <div key={review.id}>
                    <p>{review.text}</p>
                </div>
            ))}
        </div>
    );
}

export default Reviews;