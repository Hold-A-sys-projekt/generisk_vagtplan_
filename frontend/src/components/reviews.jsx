import facade from "@/util/apiFacade";
import{ useState, useEffect } from 'react';

const Reviews = () => {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        facade.fetchData("reviews/getAll", "GET")
        .then(data => setReviews(data))
        .catch(error => console.error(error));
    }, []);

    return (
        <div>
            {reviews.map((review) => (
                <div key={review.id}>
                    <h3>{review.user.username}</h3>
                    <p>says:</p>
                    <p>{review.comment}</p>
                    <p>--</p>
                </div>
            ))}
        </div>
    );
}

export default Reviews;